package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.ActividadesDto;
import com.example.api_gestion_almacen.servicios.almacenes.ActividadesServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** 
 * Controlador para gestionar actividades.
 * @author andres
 */
@RestController
@RequestMapping("/api/actividades")
public class ActividadesControlador {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ActividadesServicio actividadesServicio;
    /**
     * Lista todas las actividades asociadas a un gerente.
     * @author andres
     * 
     * @param gerenteId ID del gerente
     * @return List<ActividadesDto>
     */
    @GetMapping("/gerente/{gerenteId}")
    public ResponseEntity<?> listarPorGerente(@PathVariable Long gerenteId) {
        try {
            logger.info("Listando actividades por gerente: {}", gerenteId);
            return ResponseEntity.ok(actividadesServicio.listarPorGerente(gerenteId));
        } catch (RuntimeException ex) {
            logger.error("Error al listar actividades por gerente: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al listar actividades por gerente");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Crea una nueva actividad.
     * @author andres
     * 
     * @param dto DTO con los datos de la actividad
     * @return ActividadesDto
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ActividadesDto dto) {
        try {
            logger.info("Creando actividad: {}", dto);
            return ResponseEntity.ok(actividadesServicio.crear(dto));
        } catch (RuntimeException ex) {
            logger.error("Error al crear actividad: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al crear actividad");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Lista todas las actividades asociadas a un operario.
     * @author andres
     * 
     * @param operarioId ID del operario
     * @return List<ActividadesDto>
     */
    @GetMapping("/operario/{operarioId}")
    public ResponseEntity<?> listarParaOperario(@PathVariable Long operarioId) {
        try {
            logger.info("Listando actividades para operario: {}", operarioId);
            return ResponseEntity.ok(actividadesServicio.listarVisiblesParaOperario(operarioId));
        } catch (Exception ex) {
            logger.error("Error al listar actividades para operario: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al listar actividades para operario");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Actualiza una actividad existente.
     * @author andres
     * 
     * @param id ID de la actividad
     * @param dto DTO con los datos de la actividad
     * @return ActividadesDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ActividadesDto dto) {
        try {
            logger.info("Actualizando actividad: {}", id);
            ActividadesDto actualizado = actividadesServicio.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            logger.error("Error al actualizar actividad: {}", ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al actualizar actividad");
            return ResponseEntity.status(500).body(error);
       }
    }




























    


/** 
    
    @GetMapping
    public ResponseEntity<List<ActividadesDto>> listarTodas() {
        return ResponseEntity.ok(actividadesServicio.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadesDto> buscarPorId(@PathVariable Long id) {
        ActividadesDto dto = actividadesServicio.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadesServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ActividadesDto>> listarPorEstado(@PathVariable String estado) {
        try {
            ActividadesDto.Estado enumEstado = ActividadesDto.Estado.valueOf(estado);
            return ResponseEntity.ok(actividadesServicio.listarPorEstado(enumEstado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/visibles-operario/{operarioId}")
    public ResponseEntity<List<ActividadesDto>> listarVisiblesParaOperario(@PathVariable Long operarioId) {
        return ResponseEntity.ok(actividadesServicio.listarVisiblesParaOperario(operarioId));
    }

    @GetMapping("/todas-gerente/{gerenteId}")
    public ResponseEntity<List<ActividadesDto>> listarTodasPorGerente(@PathVariable Long gerenteId) {
        return ResponseEntity.ok(actividadesServicio.listarTodasPorGerente(gerenteId));
    }*/
}
