package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;

public class ProcesarSolicitudCommand {
    private final SolicitudDeSoporte solicitud;

    public ProcesarSolicitudCommand(SolicitudDeSoporte solicitud) {
        this.solicitud = solicitud;
    }

    public SolicitudDeSoporte getSolicitud() {
        return solicitud;
    }
}
