package com.rest.fds.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_type")
@Data
public class EntityTransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
