package com.example.api_gestion_almacen.repositorios.almacenes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_gestion_almacen.entidades.almacenes.SectoresEntidad;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones relacionadas con los sectores.
 * Proporciona métodos para buscar sectores por estado y fecha de creación.
 * @author andres
 */
@Repository
public interface SectoresRepositorio extends JpaRepository<SectoresEntidad, Long> {
    /**
     * Busca sectores por estado.
     * @param estado estado del sector
     * @return lista de sectores
     */
    List<SectoresEntidad> findByEstado(SectoresEntidad.Estado estado);
    /**
     * Busca sectores por fecha de creación.
     * @param desde fecha desde
     * @param hasta fecha hasta
     * @return lista de sectores
    
    List<SectoresEntidad> buscarPorFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta);
 */}