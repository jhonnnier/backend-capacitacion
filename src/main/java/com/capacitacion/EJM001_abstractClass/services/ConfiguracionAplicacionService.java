package com.capacitacion.EJM001_abstractClass.services;

import org.springframework.stereotype.Service;

@Service
public class ConfiguracionAplicacionService {
    public String obtenerFormatoFecha() {
        return "yyyy-MM-dd";
    }

    public String obtenerIdiomaPorDefecto() {
        return "es-CO";
    }
}
