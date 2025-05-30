package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.SectoresAlquilerDto;
import com.example.api_gestion_almacen.servicios.almacenes.SectoresAlquilerServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sectores-alquiler")
public class SectoresAlquilerControlador {
    private final SectoresAlquilerServicio servicio;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Constructor
     * @param servicio servicio de sectores de alquiler
     */
    public SectoresAlquilerControlador(SectoresAlquilerServicio servicio) {
        this.servicio = servicio;
    }
    /**
     * Lista los sectores de alquiler para un usuario
     * @param usuarioId id del usuario
     * @return lista de sectores de alquiler
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            logger.info("[listarPorUsuario] Listando sectores de alquiler para usuario id: {}", usuarioId);
            return ResponseEntity.ok(servicio.listarPorUsuario(usuarioId));
        } catch (RuntimeException ex) {
            logger.error("Error al listar sectores de alquiler por usuario: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al listar sectores de alquiler por usuario");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Crea un nuevo alquiler para un sector
     * @param dto datos del alquiler
     * @return alquiler creado
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SectoresAlquilerDto dto) {
        try {
            logger.info("[crear] Creando alquiler para sector id: {}", dto.getSectorId());
            return ResponseEntity.ok(servicio.crear(dto));
        } catch (RuntimeException ex) {
            logger.error("Error al crear alquiler: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al crear alquiler");
            return ResponseEntity.status(500).body(error);
        }
    }
    
    
    
    
    

    
    
    
    /* 
    @GetMapping
    public List<SectoresAlquilerDto> listarTodos() {
        return servicio.listarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }*/
}
