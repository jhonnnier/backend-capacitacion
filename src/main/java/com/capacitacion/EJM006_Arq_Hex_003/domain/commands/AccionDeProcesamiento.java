package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;

public interface AccionDeProcesamiento {
    void ejecutar(SolicitudDeSoporte solicitud);
}
