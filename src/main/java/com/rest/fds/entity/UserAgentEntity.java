package com.rest.fds.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_agent")
@Data
@NoArgsConstructor
public class UserAgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    @Column(name ="name", nullable = false)
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserAgentEntity(String transactionName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userAgent = transactionName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
