package com.capacitacion.EJM006_Arq_Hex_003.domain;

public class SolicitudDeSoporte {
    private Long id;
    private String tipoProblema;
    private String descripcion;
    private String prioridad;
    private String emailUsuario;
    private String funcionalidadAfectada;

    // Constructores, getters y setters
    public SolicitudDeSoporte() {
    }

    public SolicitudDeSoporte(String tipoProblema, String descripcion, String prioridad, String emailUsuario, String funcionalidadAfectada) {
        this.tipoProblema = tipoProblema;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.emailUsuario = emailUsuario;
        this.funcionalidadAfectada = funcionalidadAfectada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getFuncionalidadAfectada() {
        return funcionalidadAfectada;
    }

    public void setFuncionalidadAfectada(String funcionalidadAfectada) {
        this.funcionalidadAfectada = funcionalidadAfectada;
    }
}