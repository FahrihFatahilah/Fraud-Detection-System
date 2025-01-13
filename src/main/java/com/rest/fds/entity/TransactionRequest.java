package com.rest.fds.entity;

import com.rest.fds.entity.Model.BaseResponseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransactionRequest<T> extends BaseResponseModel<T> {

    private TransactionEntity request;

}
