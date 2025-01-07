package com.rest.fds.entity;

import com.rest.fds.entity.Model.BaseResponseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionRequest<T> extends BaseResponseModel {

    private TransactionEntity request;

}
