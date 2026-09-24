package com.capacitacion.EJM001_abstractClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanLister implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println("--- Beans gestionados por Spring ---");
        for (String beanName : beanNames) {
            System.out.println(beanName + " -> " + applicationContext.getBean(beanName).getClass().getName());
        }
        System.out.println("----------------------------------");
    }
}