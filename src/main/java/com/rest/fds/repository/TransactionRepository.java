package com.rest.fds.repository;

import com.rest.fds.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.userId = :userId AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<TransactionEntity> findHistoricalTransactions(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TransactionEntity t WHERE t.userId = :userId ORDER BY t.transactionDate DESC")
    List<TransactionEntity> findRecentTransactions(@Param("userId") String userId);

    @Query("SELECT t FROM TransactionEntity t WHERE t.userId = :userId ORDER BY t.transactionDate DESC")
    List<TransactionEntity> findLatestTransactions(@Param("userId") String userId);

    Optional<TransactionEntity> findByUserIdAndTransactionAmountAndTransactionDateAndTransactionType(String userId, Double transactionAmount, LocalDateTime transactionDate, String transactionType);
}
