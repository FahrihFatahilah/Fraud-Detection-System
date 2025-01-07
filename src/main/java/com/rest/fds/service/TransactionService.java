package com.rest.fds.service;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.entity.Model.BaseResponseModel;
import com.rest.fds.entity.TransactionEntity;
import com.rest.fds.entity.UserAgentEntity;
import com.rest.fds.repository.IpAddressRepository;
import com.rest.fds.repository.TransactionRepository;
import com.rest.fds.repository.UserAgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private static final double SUSPICIOUS_AMOUNT_THRESHOLD = 10000.0;
    private static final int LOCATION_CHANGE_THRESHOLD = 3;
    private static final int SUSPICIOUS_HOUR_START = 6;
    private static final int SUSPICIOUS_HOUR_END = 22;
    private static final Map<String, Double> VELOCITY_LIMITS = Map.of(
            "UrbanArea", 80.0,
            "RuralArea", 120.0,
            "Highway", 200.0
    );

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IpAddressRepository ipAddressRepository;

    @Autowired
    private UserAgentRepository userAgentRepository;

    public<T> BaseResponseModel<TransactionEntity> isSuspiciousTransaction(TransactionEntity currentTransaction) {
        List<String> suspiciousReasons = new ArrayList<>();
        boolean isSuspicious = false;

        List<TransactionEntity> latestTransactions = transactionRepository.findLatestTransactions(currentTransaction.getUserId());
        TransactionEntity latestTransaction = latestTransactions.isEmpty() ? null : latestTransactions.getFirst();

        if (isIpBlacklisted(currentTransaction.getIpAddress())) {
            suspiciousReasons.add("IP address is blacklisted");
            isSuspicious = true;
        }

        if (checkUserAgent(currentTransaction.getUserAgent())) {
            suspiciousReasons.add("User agent is blacklisted");
            isSuspicious = true;
        }

        if (isDuplicateTransaction(currentTransaction)) {
            suspiciousReasons.add("Duplicate transaction detected");
            isSuspicious = true;
        }

        if (latestTransaction != null && !isValidAppVersionTransition(
                latestTransaction.getAppVersion(),
                currentTransaction.getAppVersion())) {
            suspiciousReasons.add("Suspicious app version change");
            isSuspicious = true;
        }

        if (latestTransaction != null && isDeviceMismatch(latestTransaction, currentTransaction)) {
            suspiciousReasons.add("Device mismatch detected");
            isSuspicious = true;
        }

        if (!currentTransaction.getTransactionType().equalsIgnoreCase("Login") &&
                !currentTransaction.getTransactionType().equalsIgnoreCase("Activation")) {

            double totalTransactionAmount = calculateRecentTransactionsTotal(latestTransactions);
            if (totalTransactionAmount > SUSPICIOUS_AMOUNT_THRESHOLD) {
                suspiciousReasons.add("Total transaction amount exceeds threshold");
                isSuspicious = true;
            }

            if (latestTransaction != null && isTransactionAmountSuspicious(currentTransaction, latestTransaction)) {
                suspiciousReasons.add("Unusual transaction amount pattern");
                isSuspicious = true;
            }
        }

        if (latestTransaction != null) {
            if (hasFrequentLocationChange(currentTransaction.getUserId(),
                    currentTransaction.getLatitude(),
                    currentTransaction.getLongitude())) {
                suspiciousReasons.add("Frequent location changes detected");
                isSuspicious = true;
            }

            double velocity = calculateVelocity(latestTransaction, currentTransaction);
            if (velocity > getMaxAllowedVelocity(currentTransaction.getLocation())) {
                suspiciousReasons.add("Suspicious travel velocity");
                isSuspicious = true;
            }
        }

        if (isChannelMismatch(latestTransaction, currentTransaction)) {
            suspiciousReasons.add("Unusual channel transition");
            isSuspicious = true;
        }

        if (isTransactionAtOddHours(currentTransaction.getTransactionDate())) {
            suspiciousReasons.add("Transaction at unusual hours");
            isSuspicious = true;
        }

        if (isTransactionAnomalous(currentTransaction)) {
            suspiciousReasons.add("Historical transaction pattern anomaly");
            isSuspicious = true;
        }

        currentTransaction.setSuspiciousFlag(isSuspicious);

        transactionRepository.save(currentTransaction);

        if (isSuspicious) {
            String errorMessage = String.join(", ", suspiciousReasons);

            logger.warn("Suspicious transaction detected for user {}: {}",
                    currentTransaction.getUserId(), errorMessage);
            return BaseResponseModel.transactionError("FDS403", errorMessage, currentTransaction);
        } else {
            return BaseResponseModel.success(
                    "Transaction Processed Successfully", currentTransaction);
        }
    }

    private boolean isDeviceMismatch(TransactionEntity previous, TransactionEntity current) {
        return !previous.getDeviceBrand().equals(current.getDeviceBrand()) ||
                !previous.getDeviceOs().equals(current.getDeviceOs());
    }

    private boolean isChannelMismatch(TransactionEntity previous, TransactionEntity current) {
        if (previous == null) return false;
        return !previous.getChannelName().equals(current.getChannelName());
    }

    private boolean isValidAppVersionTransition(String previousVersion, String currentVersion) {
        try {
            String[] prev = previousVersion.split("\\.");
            String[] curr = currentVersion.split("\\.");

            int prevMajor = Integer.parseInt(prev[0]);
            int currMajor = Integer.parseInt(curr[0]);

            return currMajor >= prevMajor && (currMajor - prevMajor) <= 1;
        } catch (Exception e) {
            logger.error("Error comparing app versions", e);
            return false;
        }
    }

    private double calculateRecentTransactionsTotal(List<TransactionEntity> transactions) {
        return transactions.stream()
                .filter(t -> t.getTransactionAmount() != null)
                .mapToDouble(TransactionEntity::getTransactionAmount)
                .sum();
    }

    private boolean checkUserAgent(String userAgent) {
        List<UserAgentEntity> listUserAgent = userAgentRepository.findAll();
        return listUserAgent.stream()
                .anyMatch(ua -> ua.getUserAgent().equals(userAgent));
    }

    private boolean isDuplicateTransaction(TransactionEntity currentTransaction) {
        return transactionRepository.findByUserIdAndTransactionAmountAndTransactionDateAndTransactionType(
                currentTransaction.getUserId(),
                currentTransaction.getTransactionAmount(),
                currentTransaction.getTransactionDate(),
                currentTransaction.getTransactionType()
        ).isPresent();
    }

    private boolean isIpBlacklisted(String ipAddress) {
        List<IpAddressEntity> blacklistedIps = ipAddressRepository.findAll();

        return blacklistedIps.stream()
                .anyMatch(ip -> ip.getIpAddress().equals(ipAddress));
    }

    private boolean hasFrequentLocationChange(String userId, double newLatitude, double newLongitude) {
        List<TransactionEntity> recentTransactions = transactionRepository.findRecentTransactions(userId);

        long frequentLocationChangeCount = recentTransactions.stream()
                .map(transaction -> calculateHaversineDistance(
                        transaction.getLatitude(),
                        transaction.getLongitude(),
                        newLatitude,
                        newLongitude))
                .filter(distance -> distance > 100)
                .count();

        return frequentLocationChangeCount > LOCATION_CHANGE_THRESHOLD;
    }

    private double calculateVelocity(TransactionEntity previous, TransactionEntity current) {
        double distance = calculateHaversineDistance(
                previous.getLatitude(),
                previous.getLongitude(),
                current.getLatitude(),
                current.getLongitude()
        );

        long timeDifferenceInSeconds = java.time.Duration.between(
                previous.getTransactionDate(),
                current.getTransactionDate()
        ).toSeconds();

        return timeDifferenceInSeconds > 0 ? (distance / timeDifferenceInSeconds) * 3600 : 0;
    }

    private double getMaxAllowedVelocity(String location) {
        return VELOCITY_LIMITS.getOrDefault(location, 1000.0);
    }

    private boolean isTransactionAmountSuspicious(TransactionEntity current, TransactionEntity previous) {
        double currentAmount = current.getTransactionAmount() != null ? current.getTransactionAmount() : 0;
        double previousAmount = previous.getTransactionAmount() != null ? previous.getTransactionAmount() : 0;
        return Math.abs(currentAmount - previousAmount) > SUSPICIOUS_AMOUNT_THRESHOLD;
    }

    private boolean isTransactionAtOddHours(LocalDateTime transactionDate) {
        int hour = transactionDate.getHour();
        return hour < SUSPICIOUS_HOUR_START || hour > SUSPICIOUS_HOUR_END;
    }

    public boolean isTransactionAnomalous(TransactionEntity transaction) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(30);

        List<TransactionEntity> historicalTransactions = transactionRepository
                .findHistoricalTransactions(transaction.getUserId(), startDate, now);

        if (historicalTransactions.isEmpty()) {
            logger.info("No historical transactions found for user");
            return false;
        }

        double averageAmount = historicalTransactions.stream()
                .filter(t -> t.getTransactionAmount() != null)
                .mapToDouble(TransactionEntity::getTransactionAmount)
                .average()
                .orElse(0);

        double currentAmount = transaction.getTransactionAmount() != null ?
                transaction.getTransactionAmount() : 0;

        return currentAmount > averageAmount * 2;
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}