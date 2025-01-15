package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "ip_blacklist")
@Data
@NoArgsConstructor
public class IpAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name ="ip_address", nullable = false, unique = true)
    private String ipAddress;

}
