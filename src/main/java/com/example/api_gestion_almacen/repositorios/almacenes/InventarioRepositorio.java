package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.InventariosEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones relacionadas con los inventarios.
 * Proporciona métodos para buscar inventarios con el nombre del producto y el gerente.
 * @author andres
 */
@Repository
public interface InventarioRepositorio extends JpaRepository<InventariosEntidad, Long> {
    /**
     * Busca inventarios con el nombre del producto y el gerente.
     * @author andres
     * 
     * @return Una lista de inventarios con el nombre del producto y el gerente.
     */
    @org.springframework.data.jpa.repository.Query(
        value = "SELECT i.id, i.producto_id, i.gerente_id, i.cantidad_contada, i.fecha_recuento, i.observacion, p.nombre AS nombre_producto, u.nombre_completo AS nombre_gerente " +
                "FROM inventarios i " +
                "LEFT JOIN productos p ON i.producto_id = p.id " +
                "LEFT JOIN gestion_usuarios.usuarios u ON i.gerente_id = u.id",
        nativeQuery = true
    )
    java.util.List<Object[]> buscarInventarioConNombreProductoYGerente();
}
