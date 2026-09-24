package com.capacitacion.EJM002_Command.processors;

import com.capacitacion.EJM002_Command.Documento;

public abstract class ProcesadorDocumento {
    public abstract void procesar(Documento documento);

    // Ejemplo de un paso común que podría ser utilizado por las subclases
    protected void logInicioProceso(Documento documento) {
        System.out.println("Iniciando el procesamiento del documento: " + documento.getNombreArchivo() + " (" + documento.getTipo() + ")");
    }

    protected void logFinProceso(Documento documento) {
        System.out.println("Finalizado el procesamiento del documento: " + documento.getNombreArchivo());
    }
}
