package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "ip_blacklist")
public class IpAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_name")
    @SequenceGenerator(name = "seq_gen_name", sequenceName = "my_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name ="ip_address", nullable = false, unique = true)
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
