package com.capacitacion.EJM002_Command.services;

import com.capacitacion.EJM002_Command.PdfDocumento;
import org.springframework.stereotype.Service;

@Service
public class PdfService {
    public void validarPdf(PdfDocumento documento) {
        System.out.println("[PDF Service] Validando: " + documento.getNombreArchivo());
    }

    public void transformarPdf(PdfDocumento documento) {
        System.out.println("[PDF Service] Transformando: " + documento.getNombreArchivo());
    }

    public void almacenarPdf(PdfDocumento documento) {
        System.out.println("[PDF Service] Almacenando: " + documento.getNombreArchivo());
    }
}
