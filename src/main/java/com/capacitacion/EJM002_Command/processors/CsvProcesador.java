package com.capacitacion.EJM002_Command.processors;

import com.capacitacion.EJM002_Command.CsvDocumento;
import com.capacitacion.EJM002_Command.Documento;
import com.capacitacion.EJM002_Command.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("csvProcesador")
public class CsvProcesador extends ProcesadorDocumento {
    @Autowired
    private CsvService csvService;


    @Override
    public void procesar(Documento documento) {
        logInicioProceso(documento);
        csvService.parseCsv((CsvDocumento) documento);
        csvService.validarDatosCsv((CsvDocumento) documento);
        csvService.guardarDatosCsv((CsvDocumento) documento);
        logFinProceso(documento);
    }
}
