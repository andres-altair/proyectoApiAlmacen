package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.SectoresAlquilerDto;
import com.example.api_gestion_almacen.entidades.almacenes.SectoresAlquilerEntidad;
import com.example.api_gestion_almacen.entidades.almacenes.SectoresEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.SectoresAlquilerRepositorio;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Servicio para gestionar sectores de alquiler
 * @author Andres
 */
@Service
public class SectoresAlquilerServicio {
    private final SectoresAlquilerRepositorio repo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Constructor
     * @param repo repositorio de sectores de alquiler
     */
    public SectoresAlquilerServicio(SectoresAlquilerRepositorio repo) {
        this.repo = repo;
    }
    /**
     * Lista los sectores de alquiler para un usuario
     * @param usuarioId id del usuario
     * @return lista de sectores de alquiler
     */
    public List<SectoresAlquilerDto> listarPorUsuario(Long usuarioId) {
        try {
            logger.info("[listarPorUsuario] Listando sectores de alquiler para usuario id: {}", usuarioId);
            return repo.findByUsuarioId(usuarioId).stream().map(this::aDto).collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Error al listar sectores de alquiler por usuario: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar sectores de alquiler por usuario");
        }
    }
    /**
     * Crea un nuevo alquiler para un sector
     * @param dto datos del alquiler
     * @return alquiler creado
     */
    public SectoresAlquilerDto crear(SectoresAlquilerDto dto) {
        try {
            logger.info("[crear] Creando alquiler para sector id: {}", dto.getSectorId());
            SectoresAlquilerEntidad entidad = aEntidad(dto);
            entidad.setId(null);
            return aDto(repo.save(entidad));
        } catch (Exception ex) {
            logger.error("Error al crear alquiler: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al crear alquiler");
        }
    }
    /**
     * Convierte un DTO a una entidad
     * @param dto el DTO a convertir
     * @return la entidad convertida
     */
    public SectoresAlquilerEntidad aEntidad(SectoresAlquilerDto dto) {
        SectoresAlquilerEntidad entidad = new SectoresAlquilerEntidad();
        entidad.setId(dto.getId());
        entidad.setUsuarioId(dto.getUsuarioId());
        // NOTA: deberías buscar el sector por id y setearlo aquí
        SectoresEntidad sector = new SectoresEntidad();
        sector.setId(dto.getSectorId());
        entidad.setSector(sector);
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        // Lógica útil: si la fecha de inicio y fin son iguales o la fecha de fin ya pasó, el alquiler está inactivo
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null &&
            (dto.getFechaInicio().equals(dto.getFechaFin()) || dto.getFechaFin().isBefore(java.time.LocalDateTime.now()))) {
            entidad.setEstado(0);
        } else if (dto.getEstado() != null) {
            entidad.setEstado(dto.getEstado());
        }
        // Si no se cumple ninguna condición, deja el valor por defecto (1)
        return entidad;
    }
    /**
     * Convierte una entidad a un DTO
     * @param entidad la entidad a convertir
     * @return el DTO convertido
     */
    public SectoresAlquilerDto aDto(SectoresAlquilerEntidad entidad) {
        SectoresAlquilerDto dto = new SectoresAlquilerDto();
        dto.setId(entidad.getId());
        dto.setUsuarioId(entidad.getUsuarioId());
        dto.setSectorId(entidad.getSector() != null ? entidad.getSector().getId() : null);
        dto.setFechaInicio(entidad.getFechaInicio());
        dto.setFechaFin(entidad.getFechaFin());
        // Si la fecha de inicio y fin son iguales o la fecha de fin ya pasó, el alquiler está inactivo
        if (entidad.getFechaInicio() != null && entidad.getFechaFin() != null &&
            (entidad.getFechaInicio().equals(entidad.getFechaFin()) || entidad.getFechaFin().isBefore(LocalDateTime.now()))) {
            dto.setEstado(0);
        } else {
            dto.setEstado(entidad.getEstado());
        }
        return dto;
    }
    
  
    
    /** 
    public List<SectoresAlquilerDto> listarTodos() {
        return repo.findAll().stream().map(this::aDto).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }*/

    

    
}
