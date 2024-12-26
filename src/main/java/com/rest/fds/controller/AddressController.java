package com.rest.fds.controller;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.entity.LocationEntity;
import com.rest.fds.entity.Model.BaseModel;
import com.rest.fds.service.AddressService;
import com.rest.fds.util.AppHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rest.fds.util.AppHelper.*;

@RestController
@RequestMapping("/master")
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;


    @GetMapping("/location")
    public ResponseEntity<BaseModel> getListIp() {
        List<LocationEntity> ipList = addressService.getIpList();
        return ResponseEntity.ok(
                BaseModel.success(generateReferralCode(), "Transaction retrieved successfully", ipList)
        );
    }

    @PostMapping("/addLocation")
    public ResponseEntity<BaseModel> location(@RequestBody LocationEntity model) {
        if (!validateLong(String.valueOf(model.getLongitude()))){
            return ResponseEntity.badRequest().body(BaseModel.error("Invalid Longitude Format : "+ model.getLongitude()));
        }
        if (!validateLat(String.valueOf(model.getLatitude()))){
            return ResponseEntity.badRequest().body(BaseModel.error("Invalid Latitude Format : " + model.getLatitude()));
        }

        boolean status = addressService.saveLocation(model);
        if (status) {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "Location added successfully", null));
        } else {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "Location added Failed", null));
        }
    }

    @PostMapping("/addLocationList")
    public ResponseEntity<?> Location(@RequestBody @Valid List<LocationEntity> model) {
        for (LocationEntity locationEntity : model) {
            if (!validateLong(String.valueOf(locationEntity.getLongitude()))){
                return ResponseEntity.badRequest().body(BaseModel.error("Invalid Longitude Format :" + locationEntity.getLongitude()));
            }
            if (!validateLat(String.valueOf(locationEntity.getLatitude()))){
                return ResponseEntity.badRequest().body(BaseModel.error("Invalid Latitude Format : " + locationEntity.getLatitude()));
            }
        }

        boolean status = addressService.saveLocationList(model);
        if (status) {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "Location added successfully", null));
        } else {
            return ResponseEntity.ok(BaseModel.success(AppHelper.generateReferralCode(), "Location added Failed", null));
        }
    }


}
