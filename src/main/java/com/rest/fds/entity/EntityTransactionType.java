package com.rest.fds.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_type")
@Data
public class EntityTransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name ="transaction_name", nullable = false)
    private String transactionName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public EntityTransactionType(String transactionName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.transactionName = transactionName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
