package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    @Column(name = "refNo", nullable = false)
    private String transactionRefNo;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "transaction_amount", nullable = true)
    private Double transactionAmount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "userAgent", nullable = false)
    private String userAgent;

    @Column(name = "deviceOs", nullable = false)
    private String deviceOs;

    @Column(name = "deviceBrand", nullable = false)
    private String deviceBrand;

    @Column(name = "appName", nullable = false)
    private String appName;

    @Column(name = "appVersion", nullable = false)
    private String appVersion;

    @Column(name = "suspicious_flag", nullable = false)
    private Boolean suspiciousFlag = false;

    @Column(name = "channel_name", nullable = false)
    private String channelName;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}