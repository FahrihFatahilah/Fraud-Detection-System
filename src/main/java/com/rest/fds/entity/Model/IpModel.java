package com.rest.fds.entity.Model;

import jakarta.persistence.Column;

public class IpModel {
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
