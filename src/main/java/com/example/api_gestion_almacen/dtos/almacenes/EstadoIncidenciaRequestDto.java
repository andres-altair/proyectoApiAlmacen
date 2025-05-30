package com.example.api_gestion_almacen.dtos.almacenes;

import java.io.Serializable;
/**
 * Clase que representa un estado de incidencia.
 * 
 * @author Andrés
 */
public class EstadoIncidenciaRequestDto implements Serializable {
    private IncidenciaDto.Estado estado;

    public EstadoIncidenciaRequestDto() {}
    public EstadoIncidenciaRequestDto(IncidenciaDto.Estado estado) { this.estado = estado; }

    public IncidenciaDto.Estado getEstado() { return estado; }
    public void setEstado(IncidenciaDto.Estado estado) { this.estado = estado; }
}
