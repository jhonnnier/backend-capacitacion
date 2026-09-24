package com.capacitacion.EJM001_abstractClass.services;

import com.capacitacion.EJM001_abstractClass.GeneradorInformeBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GestionInformesService {

    @Autowired
    @Qualifier("generadorVentas")
    private GeneradorInformeBase generadorVentas;

    @Autowired
    @Qualifier("generadorInventario")
    private GeneradorInformeBase generadorInventario;

    public void generarInformeDeVentas() {
        System.out.println(generadorVentas.generarInformeCompleto());
    }

    public void generarInformeDeInventario() {
        System.out.println(generadorInventario.generarInformeCompleto());
    }
}
