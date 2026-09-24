package com.capacitacion.EJM006_Arq_Hex_003.domain.validators;

import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;

public interface ValidadorDeSolicitud {
    void setNext(ValidadorDeSolicitud nextValidador);

    void validar(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException;
}
