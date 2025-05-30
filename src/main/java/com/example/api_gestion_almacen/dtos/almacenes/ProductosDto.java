package com.example.api_gestion_almacen.dtos.almacenes;

import java.time.LocalDateTime;
/**
 * Clase que representa un producto.
 * 
 * @author Andrés
 */
public class ProductosDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private LocalDateTime fechaCreacion;

    public ProductosDto() {}

    public ProductosDto(Long id, String nombre, String descripcion, Integer cantidad, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters

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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}