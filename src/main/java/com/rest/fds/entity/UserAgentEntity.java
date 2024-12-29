package com.rest.fds.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_agent")
@Data
public class UserAgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name", nullable = false)
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public UserAgentEntity() {
    }

    public UserAgentEntity(String transactionName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userAgent = transactionName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
