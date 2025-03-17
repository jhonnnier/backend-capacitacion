package com.capacitacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CapacitacionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapacitacionApiApplication.class, args);
	}

}
