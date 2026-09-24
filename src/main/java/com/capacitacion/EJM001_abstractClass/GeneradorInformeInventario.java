package com.capacitacion.EJM001_abstractClass;

import com.capacitacion.EJM001_abstractClass.services.ServicioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("generadorInventario") // Le damos otro nombre
public class GeneradorInformeInventario extends GeneradorInformeBase {

    @Autowired
    private ServicioInventario servicioInventario;

    @Override
    public String generarContenido() {
        return "--- INFORME DE INVENTARIO ---\n" +
                "Productos en Stock: " + servicioInventario.obtenerCantidadProductos();
    }
}