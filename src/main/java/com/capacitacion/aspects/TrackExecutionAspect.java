package com.capacitacion.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrackExecutionAspect {

    @Around("@annotation(com.capacitacion.annotations.TrackExecution)")
    public Object trackExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("::::: Ejecutando método: '"+ className + "." + methodName + "'.");

        Object result = joinPoint.proceed(); // Ejecuta el método

        long end = System.currentTimeMillis();
//        System.out.println(":::::::: Método '" + methodName + "' en clase '" + className + "' ejecutado en " + (end - start) + " ms");
        System.out.println(":::::::: Finalizando ejecución: '"+ className + "." + methodName + "' -> Tiempo ejecución: " + (end - start) + " ms.");

        return result;
    }
}
