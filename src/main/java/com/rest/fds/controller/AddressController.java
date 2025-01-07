package com.rest.fds.controller;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.entity.LocationEntity;
import com.rest.fds.entity.Model.BaseResponseModel;
import com.rest.fds.service.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rest.fds.util.AppHelper.validateLat;
import static com.rest.fds.util.AppHelper.validateLong;

@RestController
@RequestMapping("/master")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/location")
    public ResponseEntity<BaseResponseModel<List<LocationEntity>>> getListIp() {

        List<LocationEntity> ipList = addressService.getIpList();
        return ResponseEntity.ok(BaseResponseModel.success("Transaction retrieved successfully", ipList));

    }

    @PostMapping("/addLocation")
    public ResponseEntity<BaseResponseModel<IpAddressEntity>> location(@RequestBody LocationEntity model) {

        if (!validateLong(String.valueOf(model.getLongitude()))) {

            return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid Longitude Format : " + model.getLongitude()));

        }

        if (!validateLat(String.valueOf(model.getLatitude()))) {

            return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid Latitude Format : " + model.getLatitude()));

        }

        boolean status = addressService.saveLocation(model);

        if (status) {

            return ResponseEntity.ok(BaseResponseModel.success("Location added successfully", null));

        } else {

            return ResponseEntity.ok(BaseResponseModel.success("Location added Failed", null));

        }
    }

    @PostMapping("/addLocationList")
    public ResponseEntity<?> Location(@RequestBody @Valid List<LocationEntity> model) {

        for (LocationEntity locationEntity : model) {
            if (!validateLong(String.valueOf(locationEntity.getLongitude()))) {
                return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid Longitude Format :" + locationEntity.getLongitude()));
            }
            if (!validateLat(String.valueOf(locationEntity.getLatitude()))) {
                return ResponseEntity.badRequest().body(BaseResponseModel.error("Invalid Latitude Format : " + locationEntity.getLatitude()));
            }
        }

        boolean status = addressService.saveLocationList(model);
        if (status) {
            return ResponseEntity.ok(BaseResponseModel.success("Location added successfully", null));
        } else {
            return ResponseEntity.ok(BaseResponseModel.success("Location added Failed", null));
        }
    }

}
