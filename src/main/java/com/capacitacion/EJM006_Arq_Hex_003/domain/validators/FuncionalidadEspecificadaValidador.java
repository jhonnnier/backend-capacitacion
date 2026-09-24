package com.capacitacion.EJM006_Arq_Hex_003.domain.validators;

import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FuncionalidadEspecificadaValidador implements ValidadorDeSolicitud {
    private ValidadorDeSolicitud nextValidador;

    @Override
    public void setNext(ValidadorDeSolicitud nextValidador) {
        this.nextValidador = nextValidador;
    }

    @Override
    public void validar(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException {
        if ("FUNCIONALIDAD".equalsIgnoreCase(command.getSolicitud().getTipoProblema()) && StringUtils.isEmpty(command.getSolicitud().getFuncionalidadAfectada())) {
            throw new SolicitudDeSoporteInvalidaException("Para problemas de funcionalidad, debe especificar la funcionalidad afectada.");
        }
        if (nextValidador != null) {
            nextValidador.validar(command);
        }
    }
}
