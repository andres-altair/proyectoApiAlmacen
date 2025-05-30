package com.example.api_gestion_almacen.repositorios.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.SectoresAlquilerEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones relacionadas con los sectores de alquiler.
 * Proporciona métodos para buscar sectores de alquiler por usuario.
 * @author andres
 */
@Repository
public interface SectoresAlquilerRepositorio extends JpaRepository<SectoresAlquilerEntidad, Long> {
    /**
     * Busca sectores de alquiler por usuario.
     * @param usuarioId ID del usuario.
     * @return Lista de sectores de alquiler.
     */
    List<SectoresAlquilerEntidad> findByUsuarioId(Long usuarioId);
}
