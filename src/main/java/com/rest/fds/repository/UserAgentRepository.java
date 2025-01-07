package com.rest.fds.repository;

import com.rest.fds.entity.UserAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgentRepository extends JpaRepository<UserAgentEntity, Long> {


}
