package com.capacitacion.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()); // Registra el interceptor

   /*     // Puedes especificar rutas para las que se aplicará el interceptor
        registry.addInterceptor(new OtroInterceptor())
                .addPathPatterns("/api/**") // Se aplica a todas las rutas que comiencen con /api/
                .excludePathPatterns("/api/auth/**"); // Exceptúa las rutas que comiencen con /api/auth/

        // Puedes registrar múltiples interceptores en el orden en que los necesites
        registry.addInterceptor(new TercerInterceptor()).addPathPatterns("/*");*/
    }
}
