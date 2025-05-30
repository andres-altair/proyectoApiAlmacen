package com.example.api_gestion_almacen.dtos.almacenes;

import java.time.LocalDateTime;
/**
 * Proyección para la consulta de actividades junto con información relevante del operario.
 * 
 * Esta interfaz se utiliza como proyección en consultas nativas o JPQL para obtener datos específicos
 * de actividades, incluyendo información adicional relacionada con el operario responsable.
 * 
 * Ventajas de usar proyecciones:
 * - Permite obtener solo los campos necesarios, optimizando el rendimiento.
 * - Facilita el mapeo de resultados personalizados en consultas con joins.
 * 
 * Cada método corresponde a un campo específico del resultado de la consulta.
 * Los nombres de los métodos deben coincidir exactamente con los alias definidos en la consulta SQL/JPA.
 * 
 * @author andres
 */
public interface ActividadConOperarioProjection {
    Long getId();
    String getDescripcion();
    String getEstado();
    LocalDateTime getFechaCreacion();
    Long getOperarioId();
    Long getGerenteId();
    String getOperarioNombre();
}
