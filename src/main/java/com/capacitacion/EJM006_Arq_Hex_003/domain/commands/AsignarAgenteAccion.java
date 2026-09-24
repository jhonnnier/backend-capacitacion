package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import com.capacitacion.interceptor.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("asignarAgente")
public class AsignarAgenteAccion implements AccionDeProcesamiento {
    private static final Logger logger = LoggerFactory.getLogger(AsignarAgenteAccion.class);

    @Override
    public void ejecutar(SolicitudDeSoporte solicitud) {
        System.out.println("Header = " + Context.getTenantId());
        logger.info("Asignando solicitud {} a un agente de soporte.", solicitud.getId());
        // Lógica para asignar a un agente
    }
}
