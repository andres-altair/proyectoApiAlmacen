package com.example.api_gestion_almacen.dtos.almacenes;
/**
 * Clase que representa un producto en un pedido.
 * 
 * @author Andrés
 */
public class ProductosPedidosDto {

    private Long id;
    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;
    private String estado; // reservado, enviado, entregado
    private Long operarioId;
    private Long transportistaId;

    public ProductosPedidosDto() {}

    public ProductosPedidosDto(Long id, Long pedidoId, Long productoId, Integer cantidad, String estado, Long operarioId, Long transportistaId) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.estado = estado;
        this.operarioId = operarioId;
        this.transportistaId = transportistaId;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(Long operarioId) {
        this.operarioId = operarioId;
    }

    public Long getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Long transportistaId) {
        this.transportistaId = transportistaId;
    }
}