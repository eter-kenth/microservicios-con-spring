package com.tuempresa.buscador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController // Anotación para indicar que esta clase es un controlador
public class searchserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(searchserviceApplication.class, args);
    }

    /*@GetMapping("/") // Endpoint para la ruta raíz
    public String home() {
        return "Bienvenido a la página de inicio del servicio de búsqueda!";
    }
    */
}
