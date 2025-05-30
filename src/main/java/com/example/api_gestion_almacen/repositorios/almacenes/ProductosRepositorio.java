package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.ProductosEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio para gestionar las operaciones relacionadas con los productos.
 * Proporciona métodos para buscar productos con el nombre del producto.
 * @author andres
 */
@Repository
public interface ProductosRepositorio extends JpaRepository<ProductosEntidad, Long> {
}
