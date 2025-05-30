package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.IncidenciaDto;
import com.example.api_gestion_almacen.entidades.almacenes.IncidenciasEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.IncidenciasRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Servicio para gestionar las incidencias.
 * @author andres
 */
@Service
public class IncidenciasServicio {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IncidenciasRepositorio incidenciasRepositorio;

    /**
     * Lista todas las incidencias.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    public List<IncidenciaDto> obtenerTodas() {
        try {
            List<Object[]> filas = incidenciasRepositorio.buscarTodasConNombreUsuario();
            List<IncidenciaDto> resultado = new java.util.ArrayList<>();
            for (Object[] fila : filas) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setId(((Number) fila[0]).longValue());
                dto.setUsuarioId(((Number) fila[1]).longValue());
                dto.setDescripcion((String) fila[2]);
                dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
                dto.setEstado(fila[4] != null ? IncidenciaDto.Estado.valueOf(fila[4].toString()) : null);
                dto.setNombreUsuario((String) fila[5]);
                resultado.add(dto);
            }
            logger.info("Incidencias obtenidas correctamente: {}", resultado);
            return resultado;
        } catch (Exception ex) {
            logger.error("Error al listar incidencias: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar incidencias");
        }
    }
    /**
     * Lista todas las incidencias en proceso.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    public List<IncidenciaDto> listarEnProceso() {
        try {
            List<Object[]> filas = incidenciasRepositorio.buscarEnProcesoConNombreUsuario();
            List<IncidenciaDto> resultado = new java.util.ArrayList<>();
            for (Object[] fila : filas) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setId(((Number) fila[0]).longValue());
                dto.setUsuarioId(((Number) fila[1]).longValue());
                dto.setDescripcion((String) fila[2]);
                dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
                dto.setEstado(fila[4] != null ? IncidenciaDto.Estado.valueOf(fila[4].toString()) : null);
                dto.setNombreUsuario((String) fila[5]);
                resultado.add(dto);
            }
            logger.info("Incidencias en proceso obtenidas correctamente: {}", resultado);
            return resultado;
        } catch (Exception ex) {
            logger.error("Error al listar incidencias en proceso: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar incidencias en proceso");
        }
    }
    /**
     * Lista todas las incidencias completadas.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    public List<IncidenciaDto> listarCompletadas() {
        try {
            List<Object[]> filas = incidenciasRepositorio.buscarCompletadasConNombreUsuario();
            List<IncidenciaDto> resultado = new java.util.ArrayList<>();
            for (Object[] fila : filas) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setId(((Number) fila[0]).longValue());
                dto.setUsuarioId(((Number) fila[1]).longValue());
                dto.setDescripcion((String) fila[2]);
                dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
                dto.setEstado(fila[4] != null ? IncidenciaDto.Estado.valueOf(fila[4].toString()) : null);
                dto.setNombreUsuario((String) fila[5]);
                resultado.add(dto);
            }
            logger.info("Incidencias completadas obtenidas correctamente: {}", resultado);
            return resultado;
        } catch (Exception ex) {
            logger.error("Error al listar incidencias completadas: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar incidencias completadas");
        }
    }
    /**
     * Lista todas las incidencias por usuario.
     * @author andres
     * 
     * @param usuarioId usuarioId
     * @return List<IncidenciaDto>
     */
    public List<IncidenciaDto> listarPorUsuario(Long usuarioId) {
        try {
            List<Object[]> filas = incidenciasRepositorio.buscarPorUsuarioConNombreUsuario(usuarioId);
            List<IncidenciaDto> resultado = new java.util.ArrayList<>();
            for (Object[] fila : filas) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setId(((Number) fila[0]).longValue());
                dto.setUsuarioId(((Number) fila[1]).longValue());
                dto.setDescripcion((String) fila[2]);
                dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
                dto.setEstado(fila[4] != null ? IncidenciaDto.Estado.valueOf(fila[4].toString()) : null);
                dto.setNombreUsuario((String) fila[5]);
                resultado.add(dto);
            }
            logger.info("Incidencias por usuario obtenidas correctamente: {}", resultado);
            return resultado;
        } catch (Exception ex) {
            logger.error("Error al listar incidencias por usuario: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar incidencias por usuario");
        }
    }
    /**
     * Lista todas las incidencias pendientes.
     * @author andres
     * 
     * @return List<IncidenciaDto>
     */
    public List<IncidenciaDto> listarPendientes() {
        try {
            List<Object[]> filas = incidenciasRepositorio.buscarPendientesConNombreUsuario();
            List<IncidenciaDto> resultado = new java.util.ArrayList<>();
            for (Object[] fila : filas) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setId(((Number) fila[0]).longValue());
                dto.setUsuarioId(((Number) fila[1]).longValue());
                dto.setDescripcion((String) fila[2]);
                dto.setFechaCreacion(fila[3] != null ? ((java.sql.Timestamp) fila[3]).toLocalDateTime() : null);
                dto.setEstado(fila[4] != null ? IncidenciaDto.Estado.valueOf(fila[4].toString()) : null);
                dto.setNombreUsuario((String) fila[5]);
                resultado.add(dto);
            }
            logger.info("Incidencias pendientes obtenidas correctamente: {}", resultado);
            return resultado;
        } catch (Exception ex) {
            logger.error("Error al listar incidencias pendientes: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al listar incidencias pendientes");
        }
    }
    /**
     * Crea una nueva incidencia.
     * @author andres
     * 
     * @param dto IncidenciaDto
     * @return IncidenciaDto
     */
    public IncidenciaDto crear(IncidenciaDto dto) {
        try {
            IncidenciasEntidad entidad = aEntidad(dto);
            entidad = incidenciasRepositorio.save(entidad);
            logger.info("Incidencia creada correctamente: {}", entidad);
            return aDto(entidad);
        } catch (Exception ex) {
            logger.error("Error al crear incidencia: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error al crear incidencia");
        }
    }

    /**
     * Actualiza el estado de una incidencia.
     * @author andres
     * 
     * @param id id de la incidencia
     * @param nuevoEstado nuevo estado de la incidencia
     * @return IncidenciaDto
     */
    public IncidenciaDto actualizarEstado(Long id, IncidenciaDto.Estado nuevoEstado) {
        try {
        Optional<IncidenciasEntidad> opt = incidenciasRepositorio.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Incidencia no encontrada con id: " + id);
        }
        IncidenciasEntidad entidad = opt.get();
        entidad.setEstado(nuevoEstado != null ? IncidenciasEntidad.Estado.valueOf(nuevoEstado.name()) : null);
        entidad = incidenciasRepositorio.save(entidad);
        logger.info("Incidencia actualizada correctamente: {}", entidad);
        return aDto(entidad);
        } catch (Exception ex) {
        logger.error("Error al actualizar estado de incidencia: {}", ex.getMessage(), ex);
        throw new RuntimeException("Error al actualizar estado de incidencia");
        }
    }


    


     /**
     * 
     * @param entidad IncidenciasEntidad
     * @return IncidenciaDto
     */
    private IncidenciaDto aDto(IncidenciasEntidad entidad) {
        IncidenciaDto dto = new IncidenciaDto();
        dto.setId(entidad.getId());
        dto.setUsuarioId(entidad.getUsuarioId());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaCreacion(entidad.getFechaCreacion());
        dto.setEstado(entidad.getEstado() != null ? IncidenciaDto.Estado.valueOf(entidad.getEstado().name()) : null);
        return dto;
    }

    /**
     * 
     * @param dto IncidenciaDto
     * @return IncidenciasEntidad
     */
    private IncidenciasEntidad aEntidad(IncidenciaDto dto) {
        IncidenciasEntidad entidad = new IncidenciasEntidad();
        entidad.setId(dto.getId());
        entidad.setUsuarioId(dto.getUsuarioId());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaCreacion(dto.getFechaCreacion());
        entidad.setEstado(dto.getEstado() != null ? IncidenciasEntidad.Estado.valueOf(dto.getEstado().name()) : null);
        return entidad;
    }
}
