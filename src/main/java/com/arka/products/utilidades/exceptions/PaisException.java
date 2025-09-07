package com.arka.products.utilidades.exceptions;

import org.springframework.http.HttpStatus;

public class PaisException extends RuntimeException{
    private final HttpStatus status;
    private final String errorCode;

    public PaisException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
