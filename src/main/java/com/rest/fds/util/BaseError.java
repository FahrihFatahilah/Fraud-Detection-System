package com.rest.fds.util;

import com.rest.fds.entity.Model.BaseModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class BaseError<T> {

    public ResponseEntity<BaseModel> createErrorResponse(String errorCode, String errorDescription) {
        BaseModel baseModel = new BaseModel();
        baseModel.setSuccessful(false);
        baseModel.setErrorCode(errorCode);
        baseModel.setErrorDescription(errorDescription);
        return new ResponseEntity<BaseModel>(baseModel,HttpStatusCode.valueOf(Integer.parseInt(errorCode)));
    }
    public ResponseEntity<BaseModel> createSuccessResponseResponse(String refNo,String msg,T response) {
        BaseModel<T> baseModel = new BaseModel<>();
        baseModel.setRefNo(refNo);
        baseModel.setSuccessful(true);
        baseModel.setMessage(msg);
        baseModel.setData(response);
        return new ResponseEntity<BaseModel>(baseModel,HttpStatusCode.valueOf(200));

    }
}
