package com.capacitacion.EJM002_Command.processors;

import com.capacitacion.EJM002_Command.Documento;
import com.capacitacion.EJM002_Command.XmlDocumento;
import com.capacitacion.EJM002_Command.services.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xmlProcesador")
public class XmlProcesador extends ProcesadorDocumento {
    @Autowired
    private XmlService xmlService;
    
    @Override
    public void procesar(Documento documento) {
        logInicioProceso(documento);
        xmlService.validarXml((XmlDocumento) documento);
        xmlService.transformarXml((XmlDocumento) documento);
        xmlService.indexarXml((XmlDocumento) documento);
        xmlService.archivarXml((XmlDocumento) documento);
        logFinProceso(documento);
        
    }
}
