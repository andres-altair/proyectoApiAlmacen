package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.servicios.almacenes.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Controlador para gestionar productos
 * @author Andres
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);
    @Autowired
    private ProductoServicio productoServicio;
    /**
     * Lista todos los productos.
     * @author andres
     * 
     * @return List<ProductosDto>
     */
    @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodos() {
    try {
        logger.info("Obteniendo todos los productos");
        return ResponseEntity.ok(productoServicio.obtenerTodos());
    } catch (RuntimeException ex) {
        logger.error("Error al obtener productos: {}", ex.getMessage(), ex);
        java.util.Map<String, String> error = new java.util.HashMap<>();
        error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al obtener productos");
        return ResponseEntity.status(500).body(error);
    }
    }
}
