package com.rest.fds.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.fds.entity.Model.BaseModel;
import com.rest.fds.entity.TransactionRequest;
import com.rest.fds.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restFds/Transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/check-fraud")
    public ResponseEntity<BaseModel>checkFraud(@RequestBody TransactionRequest transaction) {
        BaseModel response = transactionService.isSuspiciousTransaction(transaction.getRequest());
        try {
            ObjectMapper om = new ObjectMapper();
            String a = om.writeValueAsString(response);
            System.out.println("String json "+a);
        }catch (JsonProcessingException e){

        }
        if (response.getErrorCode() != null &&response.getErrorCode().equals("FDS403")) {
            return ResponseEntity.badRequest().body(response);  // If suspicious, return 400
        } else {
            return ResponseEntity.ok().body(response);  // If not suspicious, return 200
        }
    }

}
