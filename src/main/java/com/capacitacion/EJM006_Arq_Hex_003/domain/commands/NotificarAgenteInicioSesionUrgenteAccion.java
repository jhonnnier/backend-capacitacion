package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("notificarUrgente")
public class NotificarAgenteInicioSesionUrgenteAccion implements AccionDeProcesamiento {
    private static final Logger logger = LoggerFactory.getLogger(NotificarAgenteInicioSesionUrgenteAccion.class);

    @Override
    public void ejecutar(SolicitudDeSoporte solicitud) {
        logger.info("Notificando a agente especializado sobre problema urgente de inicio de sesión: {}", solicitud.getId());
        // Lógica para notificar al agente
    }
}
