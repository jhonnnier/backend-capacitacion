package com.capacitacion.EJM002_Command;

public abstract class Documento {
    private String nombreArchivo;
    private String tipo;

    public Documento(String nombreArchivo, String tipo) {
        this.nombreArchivo = nombreArchivo;
        this.tipo = tipo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getTipo() {
        return tipo;
    }
}
