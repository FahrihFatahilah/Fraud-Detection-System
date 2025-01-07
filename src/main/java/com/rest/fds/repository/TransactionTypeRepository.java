package com.rest.fds.repository;

import com.rest.fds.entity.EntityTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<EntityTransactionType, Long> {


}
