package com.example.api_gestion_almacen.entidades.almacenes;

import jakarta.persistence.*;

/**
 * Clase que representa una entidad ruta.
 * 
 * @author Andrés
 */
@Entity
@Table(name = "rutas")
public class RutasEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(length = 255)
    private String origen;

    @Column(length = 255)
    private String destino;

    @Column(name = "url_google_maps", columnDefinition = "TEXT")
    private String urlGoogleMaps;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public String getUrlGoogleMaps() { return urlGoogleMaps; }
    public void setUrlGoogleMaps(String urlGoogleMaps) { this.urlGoogleMaps = urlGoogleMaps; }
}