package com.example.api_gestion_almacen.dtos.almacenes;

import com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad;
/**
 * Clase que representa un pedido.
 * 
 * @author Andrés
 */
public class PedidoCrearDto {
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
    private PedidosEntidad.EstadoPedido estadoPedido;

    public PedidosEntidad.EstadoPedido getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(PedidosEntidad.EstadoPedido estadoPedido) { this.estadoPedido = estadoPedido; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}