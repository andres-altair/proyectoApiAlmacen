package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones relacionadas con los pedidos.
 * Proporciona métodos para buscar pedidos con el nombre del producto y el operario.
 * @author andres
 */
@Repository
public interface PedidosRepositorio extends JpaRepository<PedidosEntidad, Long> {

    /**
     * Busca todos los pedidos de un usuario específico.
     * @param usuarioId El ID del usuario.
     * @return Lista de entidades de pedidos.
     */
    List<PedidosEntidad> findByUsuarioId(Long usuarioId);

    /**
     * Busca pedidos con el nombre del producto.
     * @author andres
     * 
     * @param estado El estado del pedido.
     * @return Una lista de pedidos con el nombre del producto.
     */
    @org.springframework.data.jpa.repository.Query(
    value = "SELECT p.id, p.usuario_id, p.producto_id, p.cantidad, p.estado_pedido, p.operario_id, p.transportista_id, p.fecha_pedido, prod.nombre AS nombre_producto " +
            "FROM pedidos p " +
            "LEFT JOIN productos prod ON p.producto_id = prod.id " +
            "WHERE (:estado IS NULL OR p.estado_pedido = :estado)",
    nativeQuery = true
)
List<Object[]> buscarPedidosConNombreProducto(@org.springframework.data.repository.query.Param("estado") String estado);

/**
 * Busca pedidos con el nombre del producto y el nombre del transportista.
 * @author andres
 * 
 * @param estado El estado del pedido.
 * @param operarioId El ID del operario.
 * @return Una lista de pedidos con el nombre del producto y el nombre del transportista.
 */
@org.springframework.data.jpa.repository.Query(
    value = "SELECT p.id, p.usuario_id, p.producto_id, p.cantidad, p.estado_pedido, p.operario_id, p.transportista_id, p.fecha_pedido, prod.nombre AS nombre_producto, u.nombre_completo AS transportista_nombre " +
            "FROM pedidos p " +
            "LEFT JOIN productos prod ON p.producto_id = prod.id " +
            "LEFT JOIN gestion_usuarios.usuarios u ON p.transportista_id = u.id " +
            "WHERE (:estado IS NULL OR p.estado_pedido = :estado) " +
            "AND (:operarioId IS NULL OR p.operario_id = :operarioId)",
    nativeQuery = true
)
List<Object[]> buscarPedidosConNombreProductoUsuarioPorOperario(
    @org.springframework.data.repository.query.Param("estado") String estado,
    @org.springframework.data.repository.query.Param("operarioId") Long operarioId
);

/**
 * Busca pedidos con el nombre del producto y el nombre del operario.
 * @author andres
 * 
 * @param estado El estado del pedido.
 * @param transportistaId El ID del transportista.
 * @return Una lista de pedidos con el nombre del producto y el nombre del operario.
 */
@org.springframework.data.jpa.repository.Query(
    value = "SELECT p.id, p.usuario_id, p.producto_id, p.cantidad, p.estado_pedido, p.operario_id, p.transportista_id, p.fecha_pedido, prod.nombre AS nombre_producto, u.nombre_completo AS operario_nombre " +
            "FROM pedidos p " +
            "LEFT JOIN productos prod ON p.producto_id = prod.id " +
            "LEFT JOIN gestion_usuarios.usuarios u ON p.operario_id = u.id " +
            "WHERE (:estado IS NULL OR p.estado_pedido = :estado) " +
            "AND (:transportistaId IS NULL OR p.transportista_id = :transportistaId)",
    nativeQuery = true
)
List<Object[]> buscarPedidosConNombreProductoTransportista(
    @org.springframework.data.repository.query.Param("estado") String estado,
    @org.springframework.data.repository.query.Param("transportistaId") Long transportistaId
);



}