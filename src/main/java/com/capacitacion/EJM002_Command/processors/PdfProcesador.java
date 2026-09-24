package com.capacitacion.EJM002_Command.processors;

import com.capacitacion.EJM002_Command.Documento;
import com.capacitacion.EJM002_Command.PdfDocumento;
import com.capacitacion.EJM002_Command.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("pdfProcesador")
public class PdfProcesador extends ProcesadorDocumento {
    @Autowired
    private PdfService pdfService;

    @Override
    public void procesar(Documento documento) {
        logInicioProceso(documento);
        pdfService.validarPdf((PdfDocumento) documento);
        pdfService.transformarPdf((PdfDocumento) documento);
        pdfService.almacenarPdf((PdfDocumento) documento);
        logFinProceso(documento);
    }
}
