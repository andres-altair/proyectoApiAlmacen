package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.IncidenciasEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio para gestionar las operaciones relacionadas con las incidencias.
 * Proporciona métodos para buscar incidencias por estado y usuario.
 * @author andres
 */
@Repository
/**
 * Repositorio para gestionar las operaciones relacionadas con las incidencias.
 * Proporciona métodos para buscar incidencias por estado y usuario.
 * @author andres
 */
public interface IncidenciasRepositorio extends JpaRepository<IncidenciasEntidad, Long> {
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.usuario_id, i.descripcion, i.fecha_creacion, i.estado, u.nombre_completo AS nombre_usuario " +
                "FROM incidencias i " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.usuario_id = u.id "+
                "WHERE i.estado = 'en_proceso'",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarEnProcesoConNombreUsuario();

    /**
     * Busca incidencias completadas con el nombre del usuario.
     * @author andres
     * 
     * @return Una lista de incidencias completadas con el nombre del usuario.
     */
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.usuario_id, i.descripcion, i.fecha_creacion, i.estado, u.nombre_completo AS nombre_usuario " +
                "FROM incidencias i " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.usuario_id = u.id "+
                "WHERE i.estado = 'completado'",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarCompletadasConNombreUsuario();

    /**
     * Busca incidencias por usuario con el nombre del usuario.
     * @author andres
     * 
     * @param usuarioId El ID del usuario.
     * @return Una lista de incidencias con el nombre del usuario.
     */
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.usuario_id, i.descripcion, i.fecha_creacion, i.estado, u.nombre_completo AS nombre_usuario " +
                "FROM incidencias i " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.usuario_id = u.id "+
                "WHERE i.usuario_id = :usuarioId",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarPorUsuarioConNombreUsuario(@org.springframework.data.repository.query.Param("usuarioId") Long usuarioId);

    /**
     * Busca incidencias pendientes con el nombre del usuario.
     * @author andres
     * 
     * @return Una lista de incidencias pendientes con el nombre del usuario.
     */
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.usuario_id, i.descripcion, i.fecha_creacion, i.estado, u.nombre_completo AS nombre_usuario " +
                "FROM incidencias i " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.usuario_id = u.id "+
                "WHERE i.estado = 'pendiente'",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarPendientesConNombreUsuario();

    /**
     * Busca todas las incidencias con el nombre del usuario.
     * @author andres
     * 
     * @return Una lista de todas las incidencias con el nombre del usuario.
     */
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.usuario_id, i.descripcion, i.fecha_creacion, i.estado, u.nombre_completo AS nombre_usuario " +
                "FROM incidencias i " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.usuario_id = u.id",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarTodasConNombreUsuario();
}
