package com.rest.fds.util;

import com.rest.fds.entity.Model.BaseResponseModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseErrorController {

    // Handle JPA EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        BaseResponseModel<?> responseModel = new BaseResponseModel<>();
        responseModel.setSuccessful(false);
        responseModel.setRefNo(AppHelper.generateReferralCode());
        responseModel.setErrorDescription(ex.getCause().toString());
        responseModel.setMessage(ex.getMessage());
        responseModel.setTimestamp(Instant.now());

        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) throws Exception {

        Map<String, Object> body = new HashMap<>();
        BaseResponseModel<?> responseModel = new BaseResponseModel<>();
        responseModel.setSuccessful(false);
        responseModel.setRefNo(AppHelper.generateReferralCode());
        responseModel.setErrorDescription(ex.getLocalizedMessage());
        responseModel.setMessage(ex.getMessage());
        responseModel.setTimestamp(Instant.now());

        return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        BaseResponseModel<?> responseModel = new BaseResponseModel<>();
        responseModel.setSuccessful(false);
        responseModel.setRefNo(AppHelper.generateReferralCode());
        responseModel.setErrorDescription(ex.getLocalizedMessage());
        responseModel.setMessage(ex.getMessage());
        responseModel.setTimestamp(Instant.now());

        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }
}
