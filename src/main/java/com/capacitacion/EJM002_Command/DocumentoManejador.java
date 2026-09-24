package com.capacitacion.EJM002_Command;

import com.capacitacion.EJM002_Command.processors.ProcesadorDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DocumentoManejador {

    @Autowired
    private ApplicationContext applicationContext;

    public void procesarDocumento(Documento documento) {
        ProcesadorDocumento procesador = null;

        if (documento instanceof PdfDocumento) {
            procesador = (ProcesadorDocumento) applicationContext.getBean("pdfProcesador");
        } else if (documento instanceof CsvDocumento) {
            procesador = (ProcesadorDocumento) applicationContext.getBean("csvProcesador");
        } else if (documento instanceof XmlDocumento) {
            procesador = (ProcesadorDocumento) applicationContext.getBean("xmlProcesador");
        }

        if (procesador != null) {
            procesador.procesar(documento);
        } else {
            System.out.println("No se encontró un procesador para el tipo de documento: " + documento.getTipo());
        }
    }
}
