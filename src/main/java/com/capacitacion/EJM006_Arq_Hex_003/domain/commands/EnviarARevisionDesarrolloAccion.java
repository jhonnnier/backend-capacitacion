package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("enviarARevision")
public class EnviarARevisionDesarrolloAccion implements AccionDeProcesamiento {
    private static final Logger logger = LoggerFactory.getLogger(EnviarARevisionDesarrolloAccion.class);

    @Override
    public void ejecutar(SolicitudDeSoporte solicitud) {
        logger.info("Enviando solicitud de nueva característica a revisión del equipo de desarrollo: {}", solicitud.getId());
        // Lógica para enviar a revisión
    }
}
