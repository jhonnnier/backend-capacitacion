package com.capacitacion.EJM001_abstractClass;

import com.capacitacion.EJM001_abstractClass.services.ServicioVentas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("generadorVentas") // Le damos un nombre para poder inyectarlo por nombre
public class GeneradorInformeVentas extends GeneradorInformeBase {

    @Autowired
    private ServicioVentas servicioVentas;

    @Override
    public String generarContenido() {
        return "--- INFORME DE VENTAS ---\n" +
                "Total de Ventas: $" + servicioVentas.obtenerTotalVentas();
    }
}
