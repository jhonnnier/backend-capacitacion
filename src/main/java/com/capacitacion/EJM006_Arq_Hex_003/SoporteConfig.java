package com.capacitacion.EJM006_Arq_Hex_003;

import com.capacitacion.EJM006_Arq_Hex_003.domain.commands.AccionDeProcesamiento;
import com.capacitacion.EJM006_Arq_Hex_003.domain.validators.ValidadorDeSolicitud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SoporteConfig {

/*    // Spring automáticamente inyectará todas las implementaciones de ValidadorDeSolicitud
    @Bean
    public List<ValidadorDeSolicitud> validadores(List<ValidadorDeSolicitud> validadors) {
        return validadors;
    }

    // Spring automáticamente inyectará todas las implementaciones de AccionDeProcesamiento en un Map
    @Bean
    public Map<String, AccionDeProcesamiento> accionesDeProcesamiento(List<AccionDeProcesamiento> actions) {
        return actions.stream().collect(Collectors.toMap(this::getActionQualifier, action -> action));
    }

    private String getActionQualifier(AccionDeProcesamiento action) {
        // Obtiene el nombre del bean (el valor por defecto o el especificado con @Component("nombre"))
        return action.getClass().getSimpleName().replace("Accion", "").replace("Impl", "").toLowerCase();
    }*/
}
