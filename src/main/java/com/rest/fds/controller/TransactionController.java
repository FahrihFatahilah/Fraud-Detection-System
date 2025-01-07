package com.rest.fds.controller;


import com.rest.fds.entity.Model.BaseResponseModel;
import com.rest.fds.entity.TransactionEntity;
import com.rest.fds.entity.TransactionRequest;
import com.rest.fds.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restFds/Transaction")
@Slf4j
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/check-fraud")
    public ResponseEntity<BaseResponseModel<TransactionEntity>> checkFraud(@RequestBody @Valid TransactionRequest<TransactionEntity> transaction) {

        log.info("Transaction check-fraud Request {} ", transaction);

        BaseResponseModel<TransactionEntity> response = transactionService.isSuspiciousTransaction(transaction.getRequest());

        log.info("Transaction check-fraud Responses {} ", response);

        HttpStatus status = response.getErrorCode() != null && response.getErrorCode().equals("FDS403") ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

}
