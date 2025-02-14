package com.example.operador_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient  
public class OperadorserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperadorserviceApplication.class, args);
    }

    /*@RestController // Clase interna que maneja las solicitudes HTTP
    public static class HomeController {

        @GetMapping("/") // Endpoint para la ruta raíz
        public String home() {
            return "Bienvenido a la página de inicio del servicio de búsqueda!";
        }
    }
    */
}