package com.rest.fds.entity.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.fds.util.AppHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseModel<T> {

    private String refNo;
    private boolean successful;
    private String errorCode;
    private String errorDescription;
    private String message;
    private T data;
    private Instant timestamp = Instant.now();

    public static <T> BaseResponseModel<T> success(String message, T data) {
        BaseResponseModel<T> response = new BaseResponseModel<>();
        response.setSuccessful(true);
        response.setRefNo(AppHelper.generateReferralCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponseModel<T> error(String errorDescription) {
        BaseResponseModel<T> response = new BaseResponseModel<>();
        response.setSuccessful(false);
        response.setRefNo(AppHelper.generateReferralCode());
        response.setErrorDescription(errorDescription);
        return response;
    }

    public static <T> BaseResponseModel<T> transactionError(String errorCode, String errorDescription, T data) {
        BaseResponseModel<T> response = new BaseResponseModel<>();
        response.setSuccessful(false);
        response.setRefNo(AppHelper.generateReferralCode());
        response.setErrorCode(errorCode);
        response.setErrorDescription(errorDescription);
        response.setData(data);
        return response;
    }
}