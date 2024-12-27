package com.rest.fds.entity;

import com.rest.fds.entity.Model.BaseModel;

public class TransactionRequest extends BaseModel {

    private TransactionEntity request;

    public TransactionEntity getRequest() {
        return request;
    }

    public void setRequest(TransactionEntity request) {
        this.request = request;
    }
}
