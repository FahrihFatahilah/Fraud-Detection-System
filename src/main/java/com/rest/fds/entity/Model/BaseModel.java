package com.rest.fds.entity.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.rest.fds.util.AppHelper.generateReferralCode;

@Data
@NoArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_EMPTY, value = JsonInclude.Include.NON_NULL)


public class BaseModel<T> {

    public static BaseModel success(String referralCode, String message, Object data) {
        BaseModel response = new BaseModel();
        response.setSuccessful(true);
        response.setRefNo(generateReferralCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static BaseModel error(String errorDescription) {
        BaseModel response = new BaseModel();
        response.setRefNo(generateReferralCode());
        response.setSuccessful(false);
        response.setErrorDescription(errorDescription);
        return response;
    }

    private String refNo;
    private boolean successful;
    private Integer errorCode;
    private String errorDescription;
    private String message;
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
