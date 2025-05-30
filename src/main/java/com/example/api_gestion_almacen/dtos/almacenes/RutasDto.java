package com.example.api_gestion_almacen.dtos.almacenes;
/**
 * Clase que representa una ruta.
 * 
 * @author Andrés
 */
public class RutasDto {

    private Long id;
    private Long pedidoId;
    private String origen;
    private String destino;
    private String urlGoogleMaps;

    public RutasDto() {}

    public RutasDto(Long id, Long pedidoId, String origen, String destino, String urlGoogleMaps) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.origen = origen;
        this.destino = destino;
        this.urlGoogleMaps = urlGoogleMaps;
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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getUrlGoogleMaps() {
        return urlGoogleMaps;
    }

    public void setUrlGoogleMaps(String urlGoogleMaps) {
        this.urlGoogleMaps = urlGoogleMaps;
    }
}