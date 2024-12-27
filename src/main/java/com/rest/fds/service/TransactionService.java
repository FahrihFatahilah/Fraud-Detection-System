package com.rest.fds.service;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.entity.Model.BaseModel;
import com.rest.fds.entity.TransactionEntity;
import com.rest.fds.repository.IpAddressRepository;
import com.rest.fds.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.rest.fds.util.AppHelper.generateReferralCode;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    IpAddressRepository ipAddressRepository;

    public BaseModel isSuspiciousTransaction(TransactionEntity currentTransaction) {

        List<TransactionEntity> latestTransactions = transactionRepository.findLatestTransactions(currentTransaction.getUserId());
        boolean isSuspicious = false;
        TransactionEntity latestTransaction = null;

        if (latestTransactions != null && !latestTransactions.isEmpty()) {
            latestTransaction = latestTransactions.get(0);
        }

        if (isIpBlacklisted(currentTransaction.getIpAddress())) {
            logger.info("Suspicious: IP address is blacklisted.");
            isSuspicious = true;
        }

        if (!currentTransaction.getTransactionType().equalsIgnoreCase("Login") &&
                !currentTransaction.getTransactionType().equalsIgnoreCase("Activation")) {

            double totalTransactionAmount = 0.0;
            for (TransactionEntity transaction : latestTransactions) {
                totalTransactionAmount += transaction.getTransactionAmount();
            }

            double suspiciousTransactionThreshold = 10000.0;
            if (totalTransactionAmount > suspiciousTransactionThreshold) {
                logger.info("Suspicious: Total transaction amount exceeds threshold.");
                isSuspicious = true;
            }

            if (isTransactionAmountSuspicious(currentTransaction, latestTransaction)) {
                logger.info("Suspicious: Unusual transaction amount pattern detected.");
                isSuspicious = true;
            }
        }

        if (latestTransaction == null) {
            logger.info("No previous transaction found. Proceeding with blacklist and other checks.");

        } else {

            if (hasFrequentLocationChange(currentTransaction.getUserId(), currentTransaction.getLatitude(), currentTransaction.getLongitude())) {
                logger.info("Suspicious: Frequent location change detected.");
                isSuspicious = true;
            }

            if (!latestTransaction.getUserAgent().equalsIgnoreCase(currentTransaction.getUserAgent())) {
                logger.info("Suspicious: User-Agent mismatch.");
                isSuspicious = true;
            }

            double velocity = calculateVelocity(latestTransaction, currentTransaction);
            if (velocity > getMaxAllowedVelocity(currentTransaction.getLocation())) {
                logger.info("Suspicious: Velocity exceeds allowed threshold for geofenced region.");
                isSuspicious = true;
            }
        }

        if (isTransactionAtOddHours(currentTransaction.getTransactionDate())) {
            logger.info("Suspicious: Transaction at unusual hours.");
            isSuspicious = true;
        }

        if (isTransactionAnomalous(currentTransaction)) {
            logger.info("Suspicious: Historical anomaly detected.");
            isSuspicious = true;
        }

        transactionRepository.save(currentTransaction);

        if (isSuspicious) {
            currentTransaction.setSuspiciousFlag(true);
            return BaseModel.transactionError("FDS403", "Suspicious Transaction Detected", currentTransaction);
        } else {
            currentTransaction.setSuspiciousFlag(false);
            return BaseModel.success(generateReferralCode(), "Transaction Processed Successfully", currentTransaction);
        }
    }


    private boolean isDuplicateTransaction(TransactionEntity currentTransaction) {

        Optional<TransactionEntity> existingTransactionOpt = transactionRepository.findByUserIdAndTransactionAmountAndTransactionDateAndTransactionType(currentTransaction.getUserId(), currentTransaction.getTransactionAmount(), currentTransaction.getTransactionDate(), currentTransaction.getTransactionType());

        return existingTransactionOpt.isPresent();
    }

    private boolean isIpBlacklisted(String ipAddress) {
        List<IpAddressEntity> blacklistedIps = ipAddressRepository.findAll();
        return blacklistedIps.stream().anyMatch(ip -> ip.getIpAddress().equals(ipAddress));
    }

    private boolean hasFrequentLocationChange(String userId, double newLatitude, double newLongitude) {

        List<TransactionEntity> recentTransactions = transactionRepository.findRecentTransactions(userId);

        long frequentLocationChangeCount = recentTransactions.stream().map(transaction -> calculateHaversineDistance(transaction.getLatitude(), transaction.getLongitude(), newLatitude, newLongitude)).filter(distance -> distance > 100)
                .count();

        return frequentLocationChangeCount > 3;
    }

    private double calculateVelocity(TransactionEntity previous, TransactionEntity current) {
        double previousLatitude = previous.getLatitude();
        double previousLongitude = previous.getLongitude();
        double currentLatitude = current.getLatitude();
        double currentLongitude = current.getLatitude();

        double distance = calculateHaversineDistance(previousLatitude, previousLongitude, currentLatitude, currentLongitude);
        long timeDifferenceInSeconds = java.time.Duration.between(previous.getTransactionDate(), current.getTransactionDate()).toSeconds();

        return timeDifferenceInSeconds > 0 ? (distance / timeDifferenceInSeconds) * 3600 : 0;
    }

    private double getMaxAllowedVelocity(String location) {
        if (location.contains("UrbanArea")) return 80;
        if (location.contains("RuralArea")) return 120;
        return 1000;
    }

    private boolean isTransactionAmountSuspicious(TransactionEntity current, TransactionEntity previous) {
        double currentAmount = current.getTransactionAmount() != null ? current.getTransactionAmount() : 0;
        double previousAmount = previous.getTransactionAmount() != null ? previous.getTransactionAmount() : 0;
        return Math.abs(currentAmount - previousAmount) > 10000;
    }

    private boolean isTransactionAtOddHours(LocalDateTime transactionDate) {
        int hour = transactionDate.getHour();
        return hour < 6 || hour > 22;
    }

    public boolean isTransactionAnomalous(TransactionEntity transaction) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(30);
        LocalDateTime endDate = now;

        List<TransactionEntity> historicalTransactions = transactionRepository.findHistoricalTransactions(transaction.getUserId(), startDate, endDate);

        if (historicalTransactions.isEmpty()) {
            logger.info("No historical transactions found for user.");
            return false;
        }

        double averageAmount = historicalTransactions.stream().filter(t -> t.getTransactionAmount() != null).mapToDouble(TransactionEntity::getTransactionAmount).average().orElse(0);

        double currentAmount = transaction.getTransactionAmount() != null ? transaction.getTransactionAmount() : 0;

        return currentAmount > averageAmount * 2;
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
