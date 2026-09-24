package com.capacitacion.EJM002_Command.services;

import com.capacitacion.EJM002_Command.CsvDocumento;
import org.springframework.stereotype.Service;

@Service
public class CsvService {
    public void parseCsv(CsvDocumento documento) {
        System.out.println("[CSV Service] Parseando: " + documento.getNombreArchivo());
    }

    public void validarDatosCsv(CsvDocumento documento) {
        System.out.println("[CSV Service] Validando datos: " + documento.getNombreArchivo());
    }

    public void guardarDatosCsv(CsvDocumento documento) {
        System.out.println("[CSV Service] Guardando datos: " + documento.getNombreArchivo());
    }
}
