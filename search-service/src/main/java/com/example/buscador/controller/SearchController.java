package com.tuempresa.buscador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Collections;


@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // Inyección de JdbcTemplate para ejecutar el procedimiento

    @GetMapping("/home")
    public String search() {
        return "¡Bienvenido a este endpoint!";
    }

    // Ruta para obtener el stock directamente desde el procedimiento almacenado
    @GetMapping("/stock{productoId}")
    public Integer obtenerStockDesdeProcedimiento(@PathVariable int productoId) {
        try {
            // Llamada al procedimiento almacenado con el parámetro productoId
            String sql = "CALL obtener_stock(?)";  
            return jdbcTemplate.queryForObject(sql, new Object[]{productoId}, Integer.class);
        } catch (Exception e) {
            return null;  // Si ocurre algún error o el producto no existe
        }
    }

    // Ruta para realizar una venta simple
    @GetMapping("/venta/{productoId}/{cantidad}")
    public Integer realizarVentaSimple(@PathVariable int productoId, @PathVariable int cantidad) {
        try {
            String sql = "CALL realizar_venta_simple(?, ?, @resultado)";
            
            // Ejecutamos el procedimiento almacenado y obtenemos el valor del resultado
            jdbcTemplate.update(sql, productoId, cantidad);
            
            // Recuperamos el resultado del procedimiento almacenado
            Integer resultado = jdbcTemplate.queryForObject("SELECT @resultado", Integer.class);
            
            return resultado;  // Retorna 1 si la venta fue exitosa, 0 si falló
        } catch (Exception e) {
            return 0;  // Si ocurre algún error, retornamos 0
        }
    }

    // Ruta para realizar una compra simple
    @GetMapping("/compra/{productoId}/{cantidad}")
    public Integer realizarCompraSimple(@PathVariable int productoId, @PathVariable int cantidad) {
        try {
            // Llamada al procedimiento almacenado 'realizar_compra_simple'
            String sql = "CALL realizar_compra_simple(?, ?, @resultado)";
            
            // Ejecutamos el procedimiento almacenado con los parámetros de entrada
            jdbcTemplate.update(sql, productoId, cantidad);
            
            // Recuperamos el valor del parámetro de salida 'resultado'
            Integer resultado = jdbcTemplate.queryForObject("SELECT @resultado", Integer.class);
            
            return resultado;  // Retorna 1 si la compra fue exitosa, 0 si no lo fue
        } catch (Exception e) {
            return 0;  // Si ocurre algún error, retornamos 0
        }
    }

    // Ruta para agregar un producto
    @GetMapping("/agregarProducto/{nombre}/{idCategoria}/{precio}/{stock}")
    public Integer agregarProducto(@PathVariable String nombre, 
                                @PathVariable int idCategoria, 
                                @PathVariable BigDecimal precio, 
                                @PathVariable int stock) {
        try {
            String sql = "CALL agregar_producto(?, ?, ?, ?, @resultado)";
            
            // Ejecutamos el procedimiento almacenado
            jdbcTemplate.update(sql, nombre, idCategoria, precio, stock);
            
            // Recuperamos el resultado del procedimiento almacenado
            Integer resultado = jdbcTemplate.queryForObject("SELECT @resultado", Integer.class);
            
            return resultado;  // Retorna 1 si el producto fue agregado correctamente, 0 si no
        } catch (Exception e) {
            return 0;  // Si ocurre algún error, retornamos 0
        }
    }

   
}
