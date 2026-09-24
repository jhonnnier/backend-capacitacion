package com.capacitacion.exceptions;

public class ErrorDetail {
    private String codigo;
    private String mensaje;
    private String campo;

    public ErrorDetail() {
    }

    public ErrorDetail(String codigo, String mensaje, String campo) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.campo = campo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}



