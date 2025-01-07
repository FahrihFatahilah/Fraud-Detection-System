package com.rest.fds.service;

import com.rest.fds.entity.LocationEntity;
import com.rest.fds.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressService {

    @Autowired
    LocationRepository locationRepository;

    public List<LocationEntity> getIpList() {
        return locationRepository.findAll();
    }

    public boolean saveLocation(LocationEntity entity) {
        try {

            locationRepository.save(entity);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveLocationList(List<LocationEntity> entityList) {

        try {

            locationRepository.saveAll(entityList);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
