package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.RutaRespuestaDto;
import com.example.api_gestion_almacen.servicios.almacenes.RutaServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar rutas
 * @author Andres
 */
@RestController
@RequestMapping("/api/rutas")
public class RutasControlador {

    @Autowired
    private RutaServicio rutaServicio;

    private static final Logger logger = LoggerFactory.getLogger(RutasControlador.class);

    /**
     * Obtiene las rutas activas para un transportista
     * @param transportistaId id del transportista
     * @return lista de rutas activas
     */
    @GetMapping("/activas")
    public ResponseEntity<?> obtenerRutasActivasPorTransportista(@RequestParam Long transportistaId) {
        try {
            logger.info("Obteniendo rutas activas para transportista {}", transportistaId);
            List<RutaRespuestaDto> rutasDto = rutaServicio.obtenerRutasActivasPorTransportista(transportistaId);
            logger.info("Rutas activas obtenidas para transportista {}: {} rutas", transportistaId, rutasDto.size());
            return ResponseEntity.ok(rutasDto);
        } catch (Exception e) {
            logger.error("Error al obtener rutas activas para transportista {}: {}", transportistaId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al obtener rutas activas: " + e.getMessage());
        }
    }
}