package com.capacitacion.EJM002_Command.services;

import com.capacitacion.EJM002_Command.XmlDocumento;
import org.springframework.stereotype.Service;

@Service
public class XmlService {
    public void validarXml(XmlDocumento documento) {
        System.out.println("[XML Service] Validando: " + documento.getNombreArchivo());
    }

    public void transformarXml(XmlDocumento documento) {
        System.out.println("[XML Service] Transformando: " + documento.getNombreArchivo());
    }

    public void indexarXml(XmlDocumento documento) {
        System.out.println("[XML Service] Indexando: " + documento.getNombreArchivo());
    }

    public void archivarXml(XmlDocumento documento) {
        System.out.println("[XML Service] Archivando: " + documento.getNombreArchivo());
    }
}
