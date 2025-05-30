package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.RutasEntidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio para gestionar las operaciones relacionadas con las rutas.
 * Proporciona métodos para buscar rutas por pedido.
 * @author andres
 */
@Repository
public interface RutasRepositorio extends JpaRepository<RutasEntidad, Long> {
    /**
     * Busca rutas por pedido.
     * @param pedidoIds Lista de IDs de pedidos.
     * @return Lista de rutas.
     */
    List<RutasEntidad> findByPedidoIdIn(List<Long> pedidoIds);
}