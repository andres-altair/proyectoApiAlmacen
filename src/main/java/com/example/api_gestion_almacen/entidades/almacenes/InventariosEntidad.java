package com.example.api_gestion_almacen.entidades.almacenes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad inventario.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "inventarios", schema = "gestion_almacenes")
public class InventariosEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductosEntidad producto;

    @Column(name = "gerente_id", nullable = false)
    private Long gerenteId;

    @Column(name = "cantidad_contada", nullable = false)
    private Integer cantidadContada;

    @Column(name = "fecha_recuento", nullable = false)
    private LocalDateTime fechaRecuento;

    @Column(name = "observacion", length = 255)
    private String observaciones;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProductosEntidad getProducto() { return producto; }
    public void setProducto(ProductosEntidad producto) { this.producto = producto; }

    public Long getGerenteId() { return gerenteId; }
    public void setGerenteId(Long gerenteId) { this.gerenteId = gerenteId; }

    public Integer getCantidadContada() { return cantidadContada; }
    public void setCantidadContada(Integer cantidadContada) { this.cantidadContada = cantidadContada; }

    public LocalDateTime getFechaRecuento() { return fechaRecuento; }
    public void setFechaRecuento(LocalDateTime fechaRecuento) { this.fechaRecuento = fechaRecuento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
