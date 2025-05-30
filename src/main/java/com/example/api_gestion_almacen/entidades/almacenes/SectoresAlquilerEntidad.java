package com.example.api_gestion_almacen.entidades.almacenes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad sector alquiler.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "sectores_alquiler")
public class SectoresAlquilerEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId; // Referencia a gestion_usuarios.usuarios.id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private SectoresEntidad sector;

    @Column(name = "fecha_inicio", updatable = false, insertable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", updatable = false, insertable = false)
    private LocalDateTime fechaFin;

    @Column(name = "estado", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer estado = 1; // 1=activo, 0=inactivo

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public SectoresEntidad getSector() {
        return sector;
    }

    public void setSector(SectoresEntidad sector) {
        this.sector = sector;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        // Si la fecha de fin es igual a la fecha de inicio, el alquiler está inactivo
        if (this.fechaInicio != null && fechaFin != null && this.fechaInicio.equals(fechaFin)) {
            this.estado = 0;
        }
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}