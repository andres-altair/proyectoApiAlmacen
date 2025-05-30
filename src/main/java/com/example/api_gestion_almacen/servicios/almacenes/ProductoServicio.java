package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.ProductosEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.ProductosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Servicio para gestionar productos
 * @author Andres
 */
@Service
public class ProductoServicio {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductosRepositorio productosRepositorio;
    /**
     * Obtiene todos los productos.
     * @author andres
     * 
     * @return List<ProductosEntidad>
     */
    public List<ProductosEntidad> obtenerTodos() {
        try {
            logger.info("Obteniendo todos los productos");
            return productosRepositorio.findAll();
        } catch (Exception ex) {
            logger.error("Error al obtener productos: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al obtener productos");
        }
    }
}
