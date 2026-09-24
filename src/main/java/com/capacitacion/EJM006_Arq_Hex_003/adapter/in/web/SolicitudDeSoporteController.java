package com.capacitacion.EJM006_Arq_Hex_003.adapter.in.web;

import com.capacitacion.EJM006_Arq_Hex_003.application.service.ServicioDeProcesamientoDeSolicitudes;
import com.capacitacion.EJM006_Arq_Hex_003.domain.SolicitudDeSoporte;
import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.ProcesarSolicitudCommand;
import com.capacitacion.EJM006_Arq_Hex_003.domain.exceptions.SolicitudDeSoporteInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soporte")
public class SolicitudDeSoporteController {

    private final ServicioDeProcesamientoDeSolicitudes servicioDeProcesamiento;

    @Autowired
    public SolicitudDeSoporteController(ServicioDeProcesamientoDeSolicitudes servicioDeProcesamiento) {
        this.servicioDeProcesamiento = servicioDeProcesamiento;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<String> crearSolicitud(@RequestBody SolicitudDeSoporte solicitud) {
        try {
            ProcesarSolicitudCommand command = new ProcesarSolicitudCommand(solicitud);
            servicioDeProcesamiento.procesarSolicitud(command);
            return ResponseEntity.ok("Solicitud de soporte recibida y en proceso.");
        } catch (SolicitudDeSoporteInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
