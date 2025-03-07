package com.rest.fds.controller;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.entity.Model.BaseResponseModel;
import com.rest.fds.service.IpAddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master")
@Slf4j
public class IpController {

    @Autowired
    private IpAddressService ipAddressService;

    @GetMapping("/ip")
    public ResponseEntity<BaseResponseModel<List<IpAddressEntity>>> getListIp() {

        List<IpAddressEntity> ipList = ipAddressService.getIpList();

        return ResponseEntity.ok(BaseResponseModel.success("IP list retrieved successfully", ipList));

    }

    @PostMapping("/ip")
    public ResponseEntity<BaseResponseModel<IpAddressEntity>> ipAddressAdd(@RequestBody @Valid IpAddressEntity model) {

        log.info("Adding IP: {}", model.getIpAddress());

        if (!validateIp(model.getIpAddress())) {
            return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid IP address format"));
        }

        boolean status = ipAddressService.insertBlackListIp(model);

        if (status) {

            return ResponseEntity.ok(BaseResponseModel.success("IP added successfully", null));

        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponseModel.error("Failed to add IP"));

        }
    }

    @DeleteMapping("/ip")
    public ResponseEntity<BaseResponseModel<IpAddressEntity>> deleteIp(@RequestBody @Valid IpAddressEntity model) {

        log.info("Deleting IP: {}", model.getIpAddress());

        if (!validateIp(model.getIpAddress())) {

            return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid IP address format"));

        }

        boolean status = ipAddressService.deleteIpSingleIp(model.getIpAddress());

        if (status) {

            return ResponseEntity.ok(BaseResponseModel.success("IP deleted successfully", null));

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseModel.error("Failed to delete IP"));

        }
    }

    @DeleteMapping("/ip/deleteList")
    public ResponseEntity<BaseResponseModel<IpAddressEntity>> deleteList(@RequestBody List<IpAddressEntity> models) {

        log.info("Deleting IPs: {}", models);

        ipAddressService.deleteListIp(models);

        return ResponseEntity.ok(BaseResponseModel.success("IPs deleted successfully", null));

    }

    private boolean validateIp(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }
}
