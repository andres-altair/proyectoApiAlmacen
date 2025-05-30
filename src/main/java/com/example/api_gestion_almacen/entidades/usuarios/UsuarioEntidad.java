package com.example.api_gestion_almacen.entidades.usuarios;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad usuario.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "usuarios")
public class UsuarioEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombreCompleto;

    @Column(length = 9)
    private String movil;

    @Column(nullable = false, unique = true, length = 50)
    private String correoElectronico;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RolEntidad rol;

    @Column(length = 255)
    private String contrasena;

    @Column(name = "correo_confirmado")
    private Boolean correoConfirmado;
    
    @Column
    private boolean google;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto", columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public void setRol(RolEntidad rol) {
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isCorreoConfirmado() {
        return correoConfirmado != null ? correoConfirmado : false;
    }

    public void setCorreoConfirmado(Boolean correoConfirmado) {
        this.correoConfirmado = correoConfirmado != null ? correoConfirmado : false;
    }

    public boolean isGoogle() {
        return google;
    }

    public void setGoogle(boolean google) {
        this.google = google;
    }
}