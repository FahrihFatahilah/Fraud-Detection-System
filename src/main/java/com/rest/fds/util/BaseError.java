package com.rest.fds.util;

import com.rest.fds.entity.Model.BaseResponseModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class BaseError<T> {

    public ResponseEntity<BaseResponseModel> createErrorResponse(String errorCode, String errorDescription) {
        BaseResponseModel baseResponseModel = new BaseResponseModel();
        baseResponseModel.setSuccessful(false);
        baseResponseModel.setErrorCode(errorCode);
        baseResponseModel.setErrorDescription(errorDescription);
        return new ResponseEntity<BaseResponseModel>(baseResponseModel,HttpStatusCode.valueOf(Integer.parseInt(errorCode)));
    }
    public ResponseEntity<BaseResponseModel> createSuccessResponseResponse(String refNo, String msg, T response) {
        BaseResponseModel<T> baseResponseModel = new BaseResponseModel<>();
        baseResponseModel.setRefNo(refNo);
        baseResponseModel.setSuccessful(true);
        baseResponseModel.setMessage(msg);
        baseResponseModel.setData(response);
        return new ResponseEntity<BaseResponseModel>(baseResponseModel,HttpStatusCode.valueOf(200));

    }
}
