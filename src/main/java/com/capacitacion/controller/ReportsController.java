package com.capacitacion.controller;

import com.capacitacion.EJM001_abstractClass.BeanLister;
import com.capacitacion.EJM001_abstractClass.services.GestionInformesService;
import com.capacitacion.annotations.TrackExecution;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@Tag(name = "Report", description = "Rest Api for Reports")
public class ReportsController {

    @Autowired
    private GestionInformesService gestionInformesService;

    @Autowired
    private BeanLister beanLister;


    @TrackExecution
    @GetMapping("/inventario")
    public void obtenerReporteInventario() {
        gestionInformesService.generarInformeDeInventario();
        ;
    }

    @TrackExecution
    @GetMapping("/ventas")
    public void obtenerReporteVentas() {
        gestionInformesService.generarInformeDeVentas();
    }

    @TrackExecution
    @GetMapping("/beanListener")
    public void beanListener() throws Exception {
        beanLister.run();
    }
}
