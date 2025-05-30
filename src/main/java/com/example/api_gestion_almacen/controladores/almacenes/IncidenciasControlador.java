package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.IncidenciaDto;
import com.example.api_gestion_almacen.dtos.almacenes.EstadoIncidenciaRequestDto;
import com.example.api_gestion_almacen.servicios.almacenes.IncidenciasServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las incidencias.
 * @author andres
 */
@RestController
@RequestMapping("/api/incidencias")
public class IncidenciasControlador {
    @Autowired
    private IncidenciasServicio incidenciasServicio;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    /**
     * Lista todas las incidencias.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    public ResponseEntity<?> obtenerTodas() {
    try {
        List<IncidenciaDto> lista = incidenciasServicio.obtenerTodas();
        logger.info("Incidencias obtenidas correctamente: {}", lista);
        return ResponseEntity.ok(lista);
    } catch (RuntimeException ex) {
        logger.error("Error al listar incidencias: {}", ex.getMessage(), ex);   
        java.util.Map<String, String> error = new java.util.HashMap<>();
        error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al listar incidencias");
        return ResponseEntity.status(500).body(error);
    }
    }
    /**
     * Lista todas las incidencias en proceso.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    @GetMapping("/en-proceso")
    public ResponseEntity<?> listarEnProceso() {
        try {
            List<IncidenciaDto> lista = incidenciasServicio.listarEnProceso();
            logger.info("Incidencias en proceso obtenidas correctamente: {}", lista);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            logger.error("Error al listar incidencias en proceso: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al listar incidencias en proceso");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Lista todas las incidencias pendientes.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    @GetMapping("/pendientes")
    public ResponseEntity<?> listarPendientes() {
        try {
            List<IncidenciaDto> lista = incidenciasServicio.listarPendientes(); 
            logger.info("Incidencias pendientes obtenidas correctamente: {}", lista);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            logger.error("Error al listar incidencias pendientes: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al listar incidencias pendientes");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Lista todas las incidencias completadas.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    @GetMapping("/completadas")
    public ResponseEntity<?> listarCompletadas() {
        try {
            List<IncidenciaDto> lista = incidenciasServicio.listarCompletadas();
            logger.info("Incidencias completadas obtenidas correctamente: {}", lista);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            logger.error("Error al listar incidencias completadas: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al listar incidencias completadas");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Lista todas las incidencias por usuario.
     * @author andres
     * 
     * @param usuarioId Id del usuario
     * @return List<IncidenciaDto>
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<IncidenciaDto> lista = incidenciasServicio.listarPorUsuario(usuarioId);    
            logger.info("Incidencias por usuario obtenidas correctamente: {}", lista);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            logger.error("Error al listar incidencias por usuario: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al listar incidencias por usuario");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Crea una nueva incidencia.
     * @author andres
     * 
     * @param dto dto de la incidencia
     * @return IncidenciaDto
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody IncidenciaDto dto) {
        try {
            IncidenciaDto creada = incidenciasServicio.crear(dto);
            logger.info("Incidencia creada correctamente: {}", creada);
            return ResponseEntity.ok(creada);
        } catch (RuntimeException e) {
            logger.error("Error al crear incidencia: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al crear incidencia");
            return ResponseEntity.status(500).body(error);
        }
    }

    /**
     * Actualiza el estado de una incidencia.
     * @author andres
     * 
     * @param id Id de la incidencia
     * @param cuerpo dto con el estado
     * @return IncidenciaDto
     */
    @RequestMapping(value = "/{id}/estado", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody EstadoIncidenciaRequestDto cuerpo) {
        try {
            IncidenciaDto actualizada = incidenciasServicio.actualizarEstado(id, cuerpo.getEstado());
            logger.info("Incidencia actualizada correctamente: {}", actualizada);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar estado de incidencia: {}", e.getMessage(), e);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "Error al actualizar estado de incidencia");
            return ResponseEntity.status(500).body(error);
        }
    }

}