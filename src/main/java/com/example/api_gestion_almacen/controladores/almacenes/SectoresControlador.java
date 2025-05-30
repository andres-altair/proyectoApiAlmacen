package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.SectorDto;
import com.example.api_gestion_almacen.servicios.almacenes.SectoresServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador para gestionar sectores
 * @author Andres
 */
@RestController
@RequestMapping("/api/sectores")
public class SectoresControlador {
    private final SectoresServicio sectoresServicio;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public SectoresControlador(SectoresServicio sectoresServicio) {
        this.sectoresServicio = sectoresServicio;
    }

    /**
     * Busca sectores por estado
     * Permite buscar sectores por estado usando una ruta más simple.
     */
    @GetMapping("/{estado}")
    public ResponseEntity<?> buscarPorEstadoAlt(@PathVariable String estado) {
        logger.info("[buscarPorEstadoAlt] Solicitud de búsqueda de sectores por estado (alternativo): {}", estado);
        try {
            List<SectorDto> resultado = sectoresServicio.buscarPorEstado(estado);
            logger.info("[buscarPorEstadoAlt] Búsqueda completada para estado '{}', {} resultados", estado, resultado.size());
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException ex) {
            logger.error("[buscarPorEstadoAlt] Error al buscar sectores por estado '{}': {}", estado, ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al buscar sectores por estado");
            return ResponseEntity.status(500).body(error);
        }
    }
    /**
     * Actualiza un sector existente
     * @param id id del sector a actualizar
     * @param dto datos del sector a actualizar
     * @return sector actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody SectorDto dto) {
        logger.info("[actualizar] Solicitud de actualización para sector id: {}", id);
        try {
            SectorDto actualizado = sectoresServicio.actualizar(id, dto);
            if (actualizado != null) {
                logger.info("[actualizar] Sector actualizado correctamente. Id: {}", id);
                return ResponseEntity.ok(actualizado);
            } else {
                logger.warn("[actualizar] Sector no encontrado. Id: {}", id);
                java.util.Map<String, String> error = new java.util.HashMap<>();
                error.put("error", "Sector no encontrado");
                return ResponseEntity.status(404).body(error);
            }
        } catch (RuntimeException ex) {
            logger.error("[actualizar] Error al actualizar sector id '{}': {}", id, ex.getMessage(), ex);
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage() != null ? ex.getMessage() : "Error al actualizar sector");
            return ResponseEntity.status(500).body(error);
        }
    }
































/** 

    @GetMapping
    public List<SectorDto> listarTodos() {
        return sectoresServicio.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        SectorDto dto = sectoresServicio.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SectorDto crear(@RequestBody SectorDto dto) {
        return sectoresServicio.crear(dto);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sectoresServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint de búsqueda avanzada por rango de fechas
    @GetMapping("/buscar/fechas")
    public List<SectorDto> buscarPorRangoFecha(
        @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
        @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return sectoresServicio.buscarPorRangoFecha(desde, hasta);
    }*/
}
