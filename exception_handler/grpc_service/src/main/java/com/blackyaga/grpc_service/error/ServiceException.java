package com.blackyaga.grpc_service.error;


import io.grpc.Status;

public class ServiceException extends RuntimeException{
    private final Status.Code code;
    public ServiceException(Status.Code code,ErrorConstants error) {
        super(error.getMessage());
        this.code = code;
    }
    public Status.Code getCode() {
        return code;
    }
}
