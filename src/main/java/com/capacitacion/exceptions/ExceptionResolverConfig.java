package com.capacitacion.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Configuration
public class ExceptionResolverConfig {

    @Bean
    public HandlerExceptionResolver businessExceptionResolver() {
        return (request, response, handler, ex) -> {
            if (ex instanceof BusinessException) {
                BusinessException be = (BusinessException) ex;

                response.setStatus(be.getStatus().value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    ErrorResponse errorResponse = new ErrorResponse(be.getStatus().value(), be.getErrores());
                    response.getWriter().write(mapper.writeValueAsString(errorResponse));
                    response.getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Indicamos que ya se resolvió la excepción
                return new ModelAndView();
            }

            // Dejar que otros manejadores actúen
            return null;
        };
    }
}

