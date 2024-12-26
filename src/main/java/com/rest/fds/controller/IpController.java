package com.rest.fds.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.fds.entity.Model.BaseModel;
import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.service.IpAddressService;
import com.rest.fds.util.AppHelper;
import com.rest.fds.util.BaseError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/master")
public class IpController {

    private static final Logger log = LoggerFactory.getLogger(IpController.class);

    private final IpAddressService ipAddressService;

    @Autowired
    public IpController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/ip")
    public ResponseEntity<BaseModel> getListIp() {
        List<IpAddressEntity> ipList = ipAddressService.getIpList();
        return ResponseEntity.ok(
                BaseModel.success(AppHelper.generateReferralCode(), "IP list retrieved successfully", ipList)
        );
    }

    @PostMapping("/ip")
    public ResponseEntity<BaseModel> ipAddressAdd(@RequestBody @Valid IpAddressEntity model) {
        log.info("Adding IP: {}", model.getIpAddress());
        if (!validateIp(model.getIpAddress())) {
            return ResponseEntity.badRequest().body(BaseModel.error("Invalid IP address format"));
        }

        boolean status = ipAddressService.insertBlackListIp(model);
        if (status) {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "IP added successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseModel.error("Failed to add IP"));
        }
    }

    @DeleteMapping("/ip")
    public ResponseEntity<BaseModel> deleteIp(@RequestBody @Valid IpAddressEntity model) {
        log.info("Deleting IP: {}", model.getIpAddress());
        if (!validateIp(model.getIpAddress())) {
            return ResponseEntity.badRequest().body(BaseModel.error("Invalid IP address format"));
        }

        boolean status = ipAddressService.deleteIpSingleIp(model.getIpAddress());
        if (status) {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "IP deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseModel.error("Failed to delete IP"));
        }
    }

    @DeleteMapping("/ip/deleteList")
    public ResponseEntity<BaseModel> deleteList(@RequestBody List<IpAddressEntity> models) {
        log.info("Deleting IPs: {}", models);

        ipAddressService.deleteListIp(models);
        return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "IPs deleted successfully", null));
    }

    private boolean validateIp(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }
}
