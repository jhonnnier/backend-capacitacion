package com.capacitacion.EJM002_Command.controller;

import com.capacitacion.EJM002_Command.CsvDocumento;
import com.capacitacion.EJM002_Command.DocumentoManejador;
import com.capacitacion.EJM002_Command.PdfDocumento;
import com.capacitacion.EJM002_Command.XmlDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcesamientoController {

    @Autowired
    private DocumentoManejador documentoManejador;

    @GetMapping("/procesar/pdf/{nombreArchivo}")
    public String procesarPdf(@PathVariable String nombreArchivo) {
        documentoManejador.procesarDocumento(new PdfDocumento(nombreArchivo));
        return "Procesamiento de PDF iniciado.";
    }

    @GetMapping("/procesar/csv/{nombreArchivo}")
    public String procesarCsv(@PathVariable String nombreArchivo) {
        documentoManejador.procesarDocumento(new CsvDocumento(nombreArchivo));
        return "Procesamiento de CSV iniciado.";
    }

    @GetMapping("/procesar/xml/{nombreArchivo}")
    public String procesarXml(@PathVariable String nombreArchivo) {
        documentoManejador.procesarDocumento(new XmlDocumento(nombreArchivo));
        return "Procesamiento de XML iniciado.";
    }
}

