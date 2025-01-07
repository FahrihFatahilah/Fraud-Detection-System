package com.rest.fds.service;

import com.rest.fds.entity.IpAddressEntity;
import com.rest.fds.repository.IpAddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpAddressService {

    @Autowired
    private IpAddressRepository ipAddressRepository;

    public Boolean insertBlackListIp(IpAddressEntity model){

        if(validateIp(model.getIpAddress())){

            ipAddressRepository.save(model);

            return true;

        }else {

            return false;

        }
    }

    public Boolean deleteIpSingleIp(String ipAddress){

        ipAddressRepository.deleteByIpAddress(ipAddress);

        return true;
    }

    @Transactional
    public void deleteListIp(List<IpAddressEntity> ipAddressEntity) {

        List<String> ipAddresses = ipAddressEntity.stream()
                .map(IpAddressEntity::getIpAddress)
                .collect(Collectors.toList());

        ipAddressRepository.deleteByIpAddressIn(ipAddresses);

    }

    public List<IpAddressEntity> getIpList(){
        return ipAddressRepository.findAll();
    }
    private boolean validateIp(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }
}
