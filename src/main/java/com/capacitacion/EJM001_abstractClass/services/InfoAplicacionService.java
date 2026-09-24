package com.capacitacion.EJM001_abstractClass.services;

import org.springframework.stereotype.Service;

@Service
public class InfoAplicacionService {
    public String getNombreAplicacion() {
        return "Mi Sistema de Informes";
    }
}
