package com.capacitacion.EJM001_abstractClass;

import com.capacitacion.EJM001_abstractClass.services.InfoAplicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class GeneradorInformeBase {

    @Autowired
    private InfoAplicacionService infoService;

    protected String generarEncabezado() {
        return "--- INFORME ---" + "\n" +
                "Aplicación: " + infoService.getNombreAplicacion() + "\n" +
                "Fecha de Generación: " + java.time.LocalDate.now() + "\n" +
                "-------------------\n";
    }

    protected String generarPieDePagina() {
        return "\n-------------------\n" +
                "Fin del Informe.";
    }

    // Método abstracto que las clases hijas deben implementar
    public abstract String generarContenido();

    public String generarInformeCompleto() {
        return generarEncabezado() + generarContenido() + generarPieDePagina();
    }
}