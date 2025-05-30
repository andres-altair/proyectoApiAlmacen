package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.ActividadesEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Repositorio para gestionar las operaciones relacionadas con las actividades.
 * Proporciona métodos para buscar actividades por operario, gerente y estado.
 * @author andres
 */
@Repository
public interface ActividadesRepositorio extends JpaRepository<ActividadesEntidad, Long> {
    List<ActividadesEntidad> findByOperarioId(Long operarioId);
    List<ActividadesEntidad> findByGerenteId(Long gerenteId);
    List<ActividadesEntidad> findByEstado(ActividadesEntidad.Estado estado);

    /**
     * Busca actividades por gerente y operario.
     * @author andres
     * 
     * @param gerenteId El ID del gerente.
     * @return Una lista de actividades con el gerente y el operario.
     */
    @Query(
    		  value = "SELECT a.id, a.descripcion, a.estado, a.fecha_creacion, a.operario_id, a.gerente_id, u.nombre_completo AS operario_nombre " +
    		          "FROM gestion_almacenes.actividades a " +
    		          "LEFT JOIN gestion_usuarios.usuarios u ON a.operario_id = u.id " +
    		          "WHERE a.gerente_id = :gerenteId",
    		  nativeQuery = true
    		)
    		List<Object[]> buscarPorGerenteIdConNombreOperarioNativo(@Param("gerenteId") Long gerenteId);

			/**
		 * Busca actividades visibles para un operario.
		 * @author andres
		 * 
		 * @param operarioId El ID del operario.
		 * @return Una lista de actividades visibles para el operario.
		 */
			@Query(
				value = "SELECT a.id, a.descripcion, a.estado, a.fecha_creacion, a.operario_id, a.gerente_id, u.nombre_completo " +
						"FROM gestion_almacenes.actividades a " +
						"LEFT JOIN gestion_usuarios.usuarios u ON a.operario_id = u.id " +
						"WHERE a.operario_id = :operarioId OR a.estado = 'pendiente'",
				nativeQuery = true
			)
			List<Object[]> buscarVisiblesParaOperarioNativo(@Param("operarioId") Long operarioId);
}
