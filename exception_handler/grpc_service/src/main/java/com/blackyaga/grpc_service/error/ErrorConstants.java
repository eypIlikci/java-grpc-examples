package com.blackyaga.grpc_service.error;

public enum ErrorConstants {
    LOGIN("001"),
    ;
    private final String message;
    ErrorConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
