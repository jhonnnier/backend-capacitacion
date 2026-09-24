package com.capacitacion.EJM006_Arq_Hex_003.domain.validators;

import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;
import org.springframework.stereotype.Component;

@Component
public class TipoProblemaValidoValidador implements ValidadorDeSolicitud {
    private ValidadorDeSolicitud nextValidador;
    private static final String[] TIPOS_VALIDOS = {"INICIO_SESION", "FUNCIONALIDAD", "RENDIMIENTO", "CARACTERISTICA"};

    @Override
    public void setNext(ValidadorDeSolicitud nextValidador) {
        this.nextValidador = nextValidador;
    }

    @Override
    public void validar(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException {
        boolean valido = false;
        for (String tipo : TIPOS_VALIDOS) {
            if (tipo.equalsIgnoreCase(command.getSolicitud().getTipoProblema())) {
                valido = true;
                break;
            }
        }
        if (!valido) {
            throw new SolicitudDeSoporteInvalidaException("El tipo de problema no es válido.");
        }
        if (nextValidador != null) {
            nextValidador.validar(command);
        }
    }
}
