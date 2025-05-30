package com.example.api_gestion_almacen.entidades.almacenes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad pedido.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "pedidos")
public class PedidosEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido;

    @Column(name = "producto_id")
    private Long productoId;

    @Column
    private Integer cantidad;

    @Column(name = "operario_id")
    private Long operarioId;

    @Column(name = "transportista_id")
    private Long transportistaId;

    public enum EstadoPedido { pendiente, en_proceso, procesado, enviando, entregado }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }
    public EstadoPedido getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(EstadoPedido estadoPedido) { this.estadoPedido = estadoPedido; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Long getOperarioId() { return operarioId; }
    public void setOperarioId(Long operarioId) { this.operarioId = operarioId; }
    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }
}