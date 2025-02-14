package com.tuempresa.buscador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/home")
    public String search() {
        return "vienvenido a este endpoint";
    }
    
}