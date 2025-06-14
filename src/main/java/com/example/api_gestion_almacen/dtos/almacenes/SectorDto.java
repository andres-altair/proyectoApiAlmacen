package com.example.api_gestion_almacen.dtos.almacenes;

import java.time.LocalDateTime;
/**
 * Clase que representa un sector.
 * 
 * @author Andrés
 */
public class SectorDto {
    private Long id;
    
    private Double precio;
    private String estado; // Usamos String para facilitar la serialización/deserialización (libre, ocupado)
    private LocalDateTime fechaCreacion;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}