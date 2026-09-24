package com.capacitacion.EJM006_Arq_Hex_003.domain.commands;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("registrarError")
public class RegistrarErrorFuncionalidadAccion implements AccionDeProcesamiento {
    private static final Logger logger = LoggerFactory.getLogger(RegistrarErrorFuncionalidadAccion.class);

    @Override
    public void ejecutar(SolicitudDeSoporte solicitud) {
        logger.info("Registrando error de funcionalidad en el sistema de seguimiento: {}", solicitud.getId());
        // Lógica para registrar el error
    }
}
