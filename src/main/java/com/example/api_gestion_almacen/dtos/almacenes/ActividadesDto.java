package com.example.api_gestion_almacen.dtos.almacenes;

import java.time.LocalDateTime;
/**
 * Clase que representa una actividad.
 * 
 * @author Andrés
 */
public class ActividadesDto {
    private Long id;
    private String descripcion;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private Long operarioId;
    private Long gerenteId;
    private String operarioNombre;

    public enum Estado { pendiente, en_proceso, completado }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Long getOperarioId() { return operarioId; }
    public void setOperarioId(Long operarioId) { this.operarioId = operarioId; }
    public Long getGerenteId() { return gerenteId; }
    public void setGerenteId(Long gerenteId) { this.gerenteId = gerenteId; }
    public String getOperarioNombre() { return operarioNombre; }
    public void setOperarioNombre(String operarioNombre) { this.operarioNombre = operarioNombre; }


    @Override
    public String toString() {
        return "ActividadesDto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", operarioId=" + operarioId +
                ", gerenteId=" + gerenteId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActividadesDto that = (ActividadesDto) o;
        return java.util.Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
