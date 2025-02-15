package com.example.operador_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;


@RestController
@RequestMapping("/operador") // 
public class OperatorController {

    @GetMapping("/home") // Endpoint para la ruta raíz
    public String home() {
        return "Bienvenido a la página de inicio!";
    }
    @Autowired
    private JdbcTemplate jdbcTemplate; // Inyección de JdbcTemplate para ejecutar el procedimiento almacenado

    @Autowired
    private RestTemplate restTemplate; // Inyección de RestTemplate para realizar solicitudes HTTP

    @GetMapping("/venta/{productoId}/{cantidad}")
    public Integer registrarVenta(@PathVariable int productoId, @PathVariable int cantidad) {
    try {
        // Consultar stock en el microservicio 'buscador'
        String url = "http://localhost:8080/search/stock" + productoId;
        Integer stockDisponible = restTemplate.getForObject(url, Integer.class);

        if (stockDisponible == null || stockDisponible < cantidad) {
            return 0; // Retorna 0 si el stock es insuficiente o el producto no existe
        }

        // Llamada al procedimiento almacenado para registrar la venta
        String sql = "CALL IngresarVenta(?, ?, ?)";
        jdbcTemplate.update(sql, productoId, cantidad, LocalDateTime.now());

        // Hacer una solicitud HTTP a la ruta de la venta simple para actualizar el inventario
        String ventaUrl = "http://localhost:8080/search/venta/" + productoId + "/" + cantidad;
        
        // Usamos exchange para obtener más detalles de la respuesta
        ResponseEntity<Integer> response = restTemplate.exchange(
            ventaUrl,
            HttpMethod.GET,
            null,
            Integer.class
        );
        
        Integer resultadoVenta = response.getBody();
        
        if (resultadoVenta != null && resultadoVenta == 1) {
            // Si la venta fue exitosa, retornamos 1
            return 1;
        }

        return 0; // Si alguna parte del proceso falla, retornamos 0
       } catch (Exception e) {
        e.printStackTrace();  // Esto te ayudará a ver qué error ocurrió
        return 0; // Si ocurre algún error, retorna 0
       }
    }
    
    // Ruta para realizar una compra simple
    @GetMapping("/compra/{productoId}/{cantidad}")
    public Integer realizarCompraSimple(@PathVariable int productoId, @PathVariable int cantidad) {
        try {
            // Se obtiene la fecha actual
            LocalDateTime fechaActual = LocalDateTime.now();

            // Llamada al procedimiento almacenado 'IngresarCompra'
            String sql = "CALL IngresarCompra(?, ?, ?)";
            
            // Ejecutamos el procedimiento almacenado con los parámetros
            jdbcTemplate.update(sql, productoId, cantidad, fechaActual);
            
            // Llamada al microservicio de inventario (sin verificar stock)
            String inventarioUrl = "http://localhost:8080/search/compra/" + productoId + "/" + cantidad;

            // Realizamos la solicitud GET al microservicio de inventario
            restTemplate.getForObject(inventarioUrl, Void.class);

            return 1;  // Retorna 1 si la compra fue exitosa
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el error si ocurre uno
            return 0;  // Retorna 0 si ocurre un error
        }
    }

   // Ruta para realizar un ingreso de productos
   @GetMapping("/ingreso/{nombre}/{cantidad}/{idCategoria}/{precio}")
   public Integer realizarIngreso(@PathVariable String nombre, 
                                @PathVariable int cantidad, 
                                @PathVariable int idCategoria,
                                @PathVariable BigDecimal precio) {
    try {
        // Se obtiene la fecha actual
        LocalDateTime fechaIngreso = LocalDateTime.now();

        // Llamada al procedimiento almacenado 'IngresarIngreso'
        String sql = "CALL IngresarIngreso(?, ?, ?, ?)";
        
        // Ejecutamos el procedimiento almacenado con los parámetros de ingreso
        jdbcTemplate.update(sql, nombre, cantidad, fechaIngreso, idCategoria);
        
        // Aquí simplemente llamamos al microservicio de inventario para agregar el producto
        // La URL debe ser la del endpoint de agregar producto, incluyendo el precio
        String inventarioUrl = "http://localhost:8080/search/agregarProducto/" + nombre + "/" + idCategoria + "/" + precio + "/" + cantidad;
        restTemplate.getForObject(inventarioUrl, Void.class);
        
        return 1;  // Retorna 1 si el ingreso fue exitoso
    } catch (Exception e) {
        e.printStackTrace();  // Imprime el error si ocurre uno
        return 0;  // Retorna 0 si ocurre un error
    }
}


    
}