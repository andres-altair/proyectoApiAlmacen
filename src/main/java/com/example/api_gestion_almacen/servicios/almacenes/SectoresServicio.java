package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.SectorDto;
import com.example.api_gestion_almacen.entidades.almacenes.SectoresEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.SectoresRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Servicio para gestionar sectores
 * @author Andres
 */
@Service
public class SectoresServicio {
    private final SectoresRepositorio sectoresRepositorio;
    /**
     * Constructor
     * @param sectoresRepositorio repositorio de sectores
     */
    public SectoresServicio(SectoresRepositorio sectoresRepositorio) {
        this.sectoresRepositorio = sectoresRepositorio;
    }
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    /**
     * Busca sectores por estado
     * @param estado estado del sector
     * @return lista de sectores con el estado especificado
     */
    public List<SectorDto> buscarPorEstado(String estado) {
        logger.info("[buscarPorEstado] Buscando sectores con estado: {}", estado);
        try {
            List<SectorDto> resultado = sectoresRepositorio.findByEstado(SectoresEntidad.Estado.valueOf(estado)).stream().map(this::aDto).collect(Collectors.toList());
            logger.info("[buscarPorEstado] Encontrados {} sectores con estado: {}", resultado.size(), estado);
            return resultado;
        } catch (Exception ex) {
            logger.error("[buscarPorEstado] Error al buscar sectores por estado '{}': {}", estado, ex.getMessage(), ex);
            throw new RuntimeException("Error al buscar sectores por estado");
        }
    }
    /**
     * Actualiza un sector existente
     * @param id id del sector a actualizar
     * @param dto datos del sector a actualizar
     * @return sector actualizado
     */
    public SectorDto actualizar(Long id, SectorDto dto) {
        logger.info("[actualizar] Actualizando sector id: {}", id);
        try {
            SectorDto actualizado = sectoresRepositorio.findById(id).map(entidad -> {
                entidad.setEstado(SectoresEntidad.Estado.valueOf(dto.getEstado()));
                entidad.setFechaCreacion(dto.getFechaCreacion());
                return aDto(sectoresRepositorio.save(entidad));
            }).orElse(null);
            if (actualizado != null) {
                logger.info("[actualizar] Sector actualizado correctamente. Id: {}", id);
            } else {
                logger.warn("[actualizar] Sector no encontrado para actualizar. Id: {}", id);
            }
            return actualizado;
        } catch (Exception ex) {
            logger.error("[actualizar] Error al actualizar sector id '{}': {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Error al actualizar sector");
        }
    }
    /**
     * Convierte una entidad a un dto
     * @param entidad entidad a convertir
     * @return dto convertido
     */
    public SectorDto aDto(SectoresEntidad entidad) {
        SectorDto dto = new SectorDto();
        dto.setId(entidad.getId());
        dto.setPrecio(entidad.getPrecio());
        dto.setEstado(entidad.getEstado().name());
        dto.setFechaCreacion(entidad.getFechaCreacion());
        return dto;
    }








































/** 
    public List<SectorDto> listarTodos() {
        return sectoresRepositorio.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public SectorDto buscarPorId(Long id) {
        return sectoresRepositorio.findById(id).map(this::toDto).orElse(null);
    }

    public SectorDto crear(SectorDto dto) {
        SectoresEntidad entidad = toEntidad(dto);
        entidad.setId(null);
        return toDto(sectoresRepositorio.save(entidad));
    }

    

    public void eliminar(Long id) {
        sectoresRepositorio.deleteById(id);
    }

    

    public List<SectorDto> buscarPorRangoFecha(LocalDateTime desde, LocalDateTime hasta) {
        return sectoresRepositorio.buscarPorFechaCreacionBetween(desde, hasta).stream().map(this::toDto).collect(Collectors.toList());
    }

    public SectoresEntidad toEntidad(SectorDto dto) {
        SectoresEntidad entidad = new SectoresEntidad();
        entidad.setId(dto.getId());
        entidad.setPrecio(dto.getPrecio());
        entidad.setEstado(SectoresEntidad.Estado.valueOf(dto.getEstado()));
        entidad.setFechaCreacion(dto.getFechaCreacion());
        return entidad;
    }*/
}
