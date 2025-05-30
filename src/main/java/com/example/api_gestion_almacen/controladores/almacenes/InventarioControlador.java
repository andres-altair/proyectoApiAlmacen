package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.InventarioDto;
import com.example.api_gestion_almacen.servicios.almacenes.InventarioServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador para gestionar el inventario.
 * @author andres
 */
@RestController
@RequestMapping("/api/inventario")
public class InventarioControlador {

    @Autowired
    private InventarioServicio inventarioServicio;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Registra un nuevo recuento de inventario para un producto.
     *
     * @param dto Objeto {@link InventarioDto} que contiene los datos del recuento a registrar,
     *            incluyendo productoId, gerenteId, cantidadContada y observaciones.
     * @return Mensaje de éxito si el recuento se registra correctamente.
     * @throws RuntimeException si ocurre un error al registrar el recuento (por ejemplo, producto no encontrado o error en la base de datos).
     */
    @PostMapping("/recuento")
    public ResponseEntity<?> registrarRecuento(@RequestBody InventarioDto dto) {
    try {
        String mensaje = inventarioServicio.registrarRecuento(dto);
        logger.info("Recuento registrado correctamente: {}", dto);
        return ResponseEntity.ok().body(mensaje);
    } catch (RuntimeException e) {
        logger.error("Error al registrar recuento: {}", e.getMessage());
        return ResponseEntity.badRequest().body("Error al registrar recuento");
    }
}

    /**
     * Obtiene todos los recuentos de inventario registrados en la base de datos.
     *
     * @return Lista de {@link InventarioDto} que representan todos los recuentos de inventario.
     * @throws RuntimeException si ocurre un error al obtener los recuentos (por ejemplo, error en la base de datos).
     */
    @GetMapping("/recuento")
    public ResponseEntity<?> obtenerRecuentos() {
    try {
        List<InventarioDto> lista = inventarioServicio.obtenerInventarioCompleto();
        logger.info("Recuentos obtenidos correctamente: {}", lista);
        return ResponseEntity.ok(lista);
    } catch (RuntimeException e) {
        logger.error("Error al obtener recuentos: {}", e.getMessage());
        return ResponseEntity.badRequest().body("Error al obtener recuentos");
    }
}
}