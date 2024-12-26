package com.rest.fds.repository;

import com.rest.fds.entity.IpAddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddressEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ip_blacklist WHERE ip_address = :ipAddress", nativeQuery = true)
    void deleteByIpAddress(String ipAddress);

    @Transactional
    @Modifying
    @Query("DELETE FROM IpAddressEntity ip WHERE ip.ipAddress IN :ipAddresses")
    void deleteByIpAddressIn(@Param("ipAddresses") List<String> ipAddresses);
}
