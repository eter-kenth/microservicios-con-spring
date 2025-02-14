package com.example.operador_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operador") // 
public class OperatorController {

    @GetMapping("/home") // Endpoint para la ruta raíz
    public String home() {
        return "Bienvenido a la página de inicio!";
    }
}