package com.example.api_gestion_almacen.entidades.almacenes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad actividad.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "actividades")
public class ActividadesEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "operario_id")
    private Long operarioId;

    @Column(name = "gerente_id")
    private Long gerenteId;

    public enum Estado { pendiente, en_proceso, completado }

    // Getters y setters
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
}