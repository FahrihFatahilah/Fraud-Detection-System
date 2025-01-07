package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ip_blacklist")
@Data
@NoArgsConstructor
public class IpAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_name")
    @SequenceGenerator(name = "seq_gen_name", sequenceName = "my_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name ="ip_address", nullable = false, unique = true)
    private String ipAddress;

}
