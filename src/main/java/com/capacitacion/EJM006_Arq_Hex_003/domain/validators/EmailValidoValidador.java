package com.capacitacion.EJM006_Arq_Hex_003.domain.validators;

import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmailValidoValidador implements ValidadorDeSolicitud {
    private ValidadorDeSolicitud nextValidador;

    @Override
    public void setNext(ValidadorDeSolicitud nextValidador) {
        this.nextValidador = nextValidador;
    }

    @Override
    public void validar(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException {
        if (!StringUtils.isEmpty(command.getSolicitud().getEmailUsuario()) && !command.getSolicitud().getEmailUsuario().contains("@")) {
            throw new SolicitudDeSoporteInvalidaException("El formato del correo electrónico no es válido.");
        }
        if (nextValidador != null) {
            nextValidador.validar(command);
        }
    }
}
