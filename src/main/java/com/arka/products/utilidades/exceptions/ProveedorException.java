package com.arka.products.utilidades.exceptions;

import org.springframework.http.HttpStatus;

public class ProveedorException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public ProveedorException(String message, HttpStatus status, String errorCode) {
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
