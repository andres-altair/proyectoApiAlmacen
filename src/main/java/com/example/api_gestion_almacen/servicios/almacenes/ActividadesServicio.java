package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.ActividadesDto;
import com.example.api_gestion_almacen.entidades.almacenes.ActividadesEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.ActividadesRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Servicio para gestionar actividades
 * @author Andres
 */
@Service
public class ActividadesServicio {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ActividadesRepositorio actividadesRepositorio;


    /**
     * Convierte una entidad a un dto
     * @param entidad entidad a convertir
     * @return dto convertido
     */
    private ActividadesDto aDto(ActividadesEntidad entidad) {
        ActividadesDto dto = new ActividadesDto();
        dto.setId(entidad.getId());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setEstado(entidad.getEstado() != null ? ActividadesDto.Estado.valueOf(entidad.getEstado().name()) : null);
        dto.setFechaCreacion(entidad.getFechaCreacion());
        dto.setOperarioId(entidad.getOperarioId());
        dto.setGerenteId(entidad.getGerenteId());
        return dto;
    }

    /**
     * Convierte un dto a una entidad
     * @param dto dto a convertir
     * @return entidad convertida
     */
    private ActividadesEntidad aEntidad(ActividadesDto dto) {
        ActividadesEntidad entidad = new ActividadesEntidad();
        entidad.setId(dto.getId());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setEstado(dto.getEstado() != null ? ActividadesEntidad.Estado.valueOf(dto.getEstado().name()) : null);
        entidad.setFechaCreacion(dto.getFechaCreacion());
        entidad.setOperarioId(dto.getOperarioId());
        entidad.setGerenteId(dto.getGerenteId());
        return entidad;
    }
    /**
     * Lista todas las actividades asociadas a un gerente.
     * @author andres
     * 
     * @param gerenteId ID del gerente
     * @return List<ActividadesDto>
     */
    public List<ActividadesDto> listarPorGerente(Long gerenteId) {
    try {
        List<Object[]> filas = actividadesRepositorio.buscarPorGerenteIdConNombreOperarioNativo(gerenteId);
        List<ActividadesDto> resultado = new ArrayList<>();
        for (Object[] fila : filas) {
            ActividadesDto dto = new ActividadesDto();
            dto.setId(((Number) fila[0]).longValue());
            dto.setDescripcion((String) fila[1]);
            dto.setEstado(fila[2] != null ? ActividadesDto.Estado.valueOf(fila[2].toString()) : null);
            dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
            dto.setOperarioId(fila[4] != null ? ((Number) fila[4]).longValue() : null);
            dto.setGerenteId(fila[5] != null ? ((Number) fila[5]).longValue() : null);
            dto.setOperarioNombre((String) fila[6]);
            resultado.add(dto);
        }
        logger.info("Actividades obtenidas correctamente: {}", resultado);
        return resultado;
    } catch (Exception ex) {
        logger.error("Error al listar actividades por gerente: {}", ex.getMessage(), ex);
        throw new RuntimeException("Error al listar actividades por gerente");
    }
}
    /**
     * Crea una nueva actividad.
     * @author andres
     * 
     * @param dto DTO con los datos de la actividad
     * @return ActividadesDto
     */
    public ActividadesDto crear(ActividadesDto dto) {
        try {
            ActividadesEntidad entidad = aEntidad(dto);
            entidad.setEstado(ActividadesEntidad.Estado.pendiente); // Forzar pendiente
            entidad.setFechaCreacion(LocalDateTime.now()); // Fecha actual
            ActividadesEntidad guardada = actividadesRepositorio.save(entidad);
            logger.info("Actividad creada correctamente: {}", guardada);
            return aDto(guardada);
        } catch (Exception ex) {
            logger.error("Error al crear actividad: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al crear actividad");
        }
    }
    /**
     * Lista todas las actividades asociadas a un operario.
     * @author andres
     * 
     * @param operarioId ID del operario    
     * @return List<ActividadesDto>
     */
    public List<ActividadesDto> listarVisiblesParaOperario(Long operarioId) {
    try {
        List<Object[]> filas = actividadesRepositorio.buscarVisiblesParaOperarioNativo(operarioId);
        List<ActividadesDto> resultado = new ArrayList<>();
        for (Object[] fila : filas) {
            ActividadesDto dto = new ActividadesDto();
            dto.setId(((Number) fila[0]).longValue());
            dto.setDescripcion((String) fila[1]);
            dto.setEstado(fila[2] != null ? ActividadesDto.Estado.valueOf(fila[2].toString()) : null);
            dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
            dto.setOperarioId(fila[4] != null ? ((Number) fila[4]).longValue() : null);
            dto.setGerenteId(fila[5] != null ? ((Number) fila[5]).longValue() : null);
            dto.setOperarioNombre((String) fila[6]);
            resultado.add(dto);
        }
        logger.info("Actividades visibles para operario obtenidas correctamente: {}", resultado);
        return resultado;
    } catch (Exception ex) {
        logger.error("Error al listar actividades visibles para operario: {}", ex.getMessage(), ex);
        throw new RuntimeException("Error al listar actividades visibles para operario");
    }
}
    /**
     * Actualiza una actividad existente.
     * @author andres
     * 
     * @param id ID de la actividad
     * @param dto DTO con los datos a actualizar
     * @return ActividadesDto con los datos actualizados
     */
    public ActividadesDto actualizar(Long id, ActividadesDto dto) {
        try {
        return actividadesRepositorio.findById(id)
                .map(entidad -> {
                    // Solo actualiza el estado si viene en el DTO
                    if (dto.getEstado() != null) {
                        entidad.setEstado(ActividadesEntidad.Estado.valueOf(dto.getEstado().name()));
                    }
                    // Solo actualiza el operarioId si viene en el DTO
                    if (dto.getOperarioId() != null) {
                        entidad.setOperarioId(dto.getOperarioId());
                    }
                    // NO toques descripcion, gerenteId, fechaCreacion, etc.
                    logger.info("Actividad actualizada: {}", entidad);
                    return aDto(actividadesRepositorio.save(entidad));
                })
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con id: " + id));
        } catch (Exception ex) {
            logger.error("Error al actualizar actividad: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al actualizar actividad con id: " + id);
        }
    }










    /* 
    
    public List<ActividadesDto> listarTodas() {
        return actividadesRepositorio.findAll().stream().map(this::aDto).collect(Collectors.toList());
    }

    

    public ActividadesDto buscarPorId(Long id) {
        return actividadesRepositorio.findById(id).map(this::aDto).orElse(null);
    }

    

    

    public List<ActividadesDto> listarPorEstado(ActividadesDto.Estado estado) {
        return actividadesRepositorio.findByEstado(ActividadesEntidad.Estado.valueOf(estado.name())).stream().map(this::aDto).collect(Collectors.toList());
    }

    

    public void eliminar(Long id) {
        actividadesRepositorio.deleteById(id);
    }*/


    // Devuelve todas las actividades en pendiente, en_proceso y todas las asignadas al operario
    //public List<ActividadesDto> listarVisiblesParaOperario(Long operarioId) {
    //    List<ActividadesDto> resultado = listarPorEstado(ActividadesDto.Estado.pendiente);
    //    resultado.addAll(listarPorEstado(ActividadesDto.Estado.en_proceso));
    //    List<ActividadesDto> propias = listarPorOperario(operarioId);
    //    for (ActividadesDto dto : propias) {
    //        if (resultado.stream().noneMatch(a -> a.getId().equals(dto.getId()))) {
    //            resultado.add(dto);
    //        }
    //    }
    //    return resultado;
    //}

    /* Devuelve todas las actividades asociadas a un gerente, independientemente del estado
    public List<ActividadesDto> listarTodasPorGerente(Long gerenteId) {
        return actividadesRepositorio.findByGerenteId(gerenteId).stream().map(this::aDto).collect(Collectors.toList());
    }
    */}
