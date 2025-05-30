package com.example.api_gestion_almacen.dtos.almacenes;
/**
 * Clase que representa una asignación de usuario.
 * 
 * @author Andrés
 */
public class AsignacionUsuarioDto {
    private Long usuarioId; // Puede ser operarioId o transportistaId

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}