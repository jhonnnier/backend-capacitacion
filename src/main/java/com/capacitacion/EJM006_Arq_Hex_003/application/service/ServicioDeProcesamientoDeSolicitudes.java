package com.capacitacion.EJM006_Arq_Hex_003.application.service;

import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.AccionDeProcesamiento;
import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;
import com.capacitacion.EJM006_Arq_Hex_003.domain.validators.ValidadorDeSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServicioDeProcesamientoDeSolicitudes {

    private final List<ValidadorDeSolicitud> validadores;
    private final Map<String, AccionDeProcesamiento> accionesDeProcesamiento;

    @Autowired
    public ServicioDeProcesamientoDeSolicitudes(List<ValidadorDeSolicitud> validadores, Map<String, AccionDeProcesamiento> accionesDeProcesamiento) {
        this.validadores = validadores;
        this.accionesDeProcesamiento = accionesDeProcesamiento;
    }

    public void procesarSolicitud(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException {
        SolicitudDeSoporte solicitud = excecuteValidators(command);

        // Lógica para determinar las acciones de procesamiento basadas en el tipo y prioridad
        excecuteCommandProcessorActions(solicitud);

        // Aquí podrías guardar la solicitud en la base de datos usando un repositorio (adaptador secundario)
        System.out.println("Solicitud procesada con ID: " + solicitud.getId());
    }

    /*
    Uso del patros "Command"
     */
    private void excecuteCommandProcessorActions(SolicitudDeSoporte solicitud) {
        if ("INICIO_SESION".equalsIgnoreCase(solicitud.getTipoProblema()) && "ALTA".equalsIgnoreCase(solicitud.getPrioridad())) {
            accionesDeProcesamiento.get("notificarUrgente").ejecutar(solicitud);
            accionesDeProcesamiento.get("asignarAgente").ejecutar(solicitud);
        } else if ("FUNCIONALIDAD".equalsIgnoreCase(solicitud.getTipoProblema())) {
            accionesDeProcesamiento.get("registrarError").ejecutar(solicitud);
            accionesDeProcesamiento.get("asignarAgente").ejecutar(solicitud);
        } else if ("CARACTERISTICA".equalsIgnoreCase(solicitud.getTipoProblema())) {
            accionesDeProcesamiento.get("enviarARevision").ejecutar(solicitud);
        } else {
            accionesDeProcesamiento.get("asignarAgente").ejecutar(solicitud);
        }
    }

    /*
    Uso del patron "Cadena de Responsabilidades"
     */
    private SolicitudDeSoporte excecuteValidators(ProcesarSolicitudCommand command) throws SolicitudDeSoporteInvalidaException {
        // Configurar la cadena de validadores
        if (!validadores.isEmpty()) {
            for (int i = 0; i < validadores.size() - 1; i++) {
                validadores.get(i).setNext(validadores.get(i + 1));
            }
            validadores.get(0).validar(command);
        }

        SolicitudDeSoporte solicitud = command.getSolicitud();
        return solicitud;
    }
}
