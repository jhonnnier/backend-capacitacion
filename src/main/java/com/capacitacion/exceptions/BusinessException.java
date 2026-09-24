package com.capacitacion.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final List<ErrorDetail> errores;

    public BusinessException(HttpStatus status, List<ErrorDetail> errores) {
        super("Error de negocio");
        this.status = status;
        this.errores = errores;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<ErrorDetail> getErrores() {
        return errores;
    }
}

