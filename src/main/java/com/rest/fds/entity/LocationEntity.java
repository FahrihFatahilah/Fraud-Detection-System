package com.rest.fds.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "location_list")
@Data
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_name")
    @SequenceGenerator(name = "seq_gen_name", sequenceName = "my_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name ="latitude", nullable = false,unique = true)
    private Double latitude;

    @Column(name ="longitude", nullable = false, unique = true)
    private Double longitude;

    @Column(name ="location", nullable = false)
    private String location;


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
