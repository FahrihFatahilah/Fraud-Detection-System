package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "transaction_amount", nullable = true)
    private Double transactionAmount;

    @Column(name = "transaction_type", nullable = false)
    private String transationType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "userAgent", nullable = false)
    private Boolean userAgent = false;

    @Column(name = "deviceOs", nullable = false)
    private Boolean deviceOs = false;

    @Column(name = "deviceBrand", nullable = false)
    private Boolean deviceBrand = false;

    @Column(name = "appName", nullable = false)
    private Boolean appName = false;

    @Column(name = "appVersion", nullable = false)
    private Boolean appVersion = false;

    @Column(name = "suspicious_flag", nullable = false)
    private Boolean suspiciousFlag = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}