package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "location_list")
@Data
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name ="latitude", nullable = false,unique = true)
    private Double latitude;

    @Column(name ="longitude", nullable = false, unique = true)
    private Double longitude;

    @Column(name ="location", nullable = false)
    private String location;

}
