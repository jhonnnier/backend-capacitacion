package com.capacitacion.exceptions;

import java.util.List;

public class ErrorResponse {
    private int status;
    private List<ErrorDetail> errores;

    public ErrorResponse(int status, List<ErrorDetail> errores) {
        this.status = status;
        this.errores = errores;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ErrorDetail> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorDetail> errores) {
        this.errores = errores;
    }
}