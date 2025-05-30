package com.example.api_gestion_almacen.entidades.usuarios;

import jakarta.persistence.*;

/**
 * Clase que representa una entidad rol.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "roles")
public class RolEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
	@Column(length = 255)
    private String descripcion;

    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}