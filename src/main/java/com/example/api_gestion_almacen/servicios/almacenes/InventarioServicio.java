package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.InventarioDto;
import com.example.api_gestion_almacen.entidades.almacenes.InventariosEntidad;
import com.example.api_gestion_almacen.entidades.almacenes.ProductosEntidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.api_gestion_almacen.repositorios.almacenes.InventarioRepositorio;
import com.example.api_gestion_almacen.repositorios.almacenes.ProductosRepositorio;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Servicio para gestionar el inventario.
 * @author andres
 */
@Service
public class InventarioServicio {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;
    @Autowired
    private ProductosRepositorio productosRepositorio;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Registra un nuevo recuento de inventario para un producto.
     *
     * @param dto Objeto {@link InventarioDto} que contiene los datos del recuento a registrar,
     *            incluyendo productoId, gerenteId, cantidadContada y observaciones.
     * @return Mensaje de éxito si el recuento se registra correctamente.
     * @throws RuntimeException si ocurre un error al registrar el recuento (por ejemplo, producto no encontrado o error en la base de datos).
     */
    public String registrarRecuento(InventarioDto dto) {
        try {
            Long productoId = dto.getProductoId();
            Long gerenteId = dto.getGerenteId();
            Integer cantidadContada = dto.getCantidadContada();
            String observaciones = dto.getObservaciones();

            ProductosEntidad producto = productosRepositorio.findById(productoId).orElseThrow();

            InventariosEntidad recuento = new InventariosEntidad();
            recuento.setProducto(producto);
            recuento.setGerenteId(gerenteId);
            recuento.setCantidadContada(cantidadContada);
            recuento.setFechaRecuento(LocalDateTime.now());
            recuento.setObservaciones(observaciones);

            inventarioRepositorio.save(recuento);
            return "Recuento registrado correctamente";
        } catch (Exception e) {
            logger.error("Error al registrar recuento: {}", e.getMessage());
            throw new RuntimeException("Error al registrar recuento");
        }
    }

    /**
     * Obtiene todos los recuentos de inventario registrados en la base de datos.
     *
     * @return Lista de {@link InventarioDto} que representan todos los recuentos de inventario.
     * @throws RuntimeException si ocurre un error al obtener los recuentos (por ejemplo, error en la base de datos).
     */
    public List<InventarioDto> obtenerInventarioCompleto() {
    try {
        List<Object[]> filas = inventarioRepositorio.buscarInventarioConNombreProductoYGerente();
        List<InventarioDto> resultado = new ArrayList<>();
        for (Object[] fila : filas) {
            InventarioDto dto = new InventarioDto();
            dto.setId(((Number) fila[0]).longValue());
            dto.setProductoId(((Number) fila[1]).longValue());
            dto.setGerenteId(((Number) fila[2]).longValue());
            dto.setCantidadContada(fila[3] != null ? ((Number) fila[3]).intValue() : null);
            dto.setFechaRecuento(fila[4] != null ? ((java.sql.Timestamp) fila[4]).toLocalDateTime() : null);
            dto.setObservaciones((String) fila[5]);
            dto.setNombreProducto((String) fila[6]);
            dto.setNombreGerente((String) fila[7]); // Nuevo campo mapeado
            resultado.add(dto);
        }
        return resultado;
    } catch (Exception e) {
        logger.error("Error al obtener inventario: {}", e.getMessage());
        throw new RuntimeException("Error al obtener inventario");
    }
}

    /**
     * Convierte una entidad {@link InventariosEntidad} a un DTO {@link InventarioDto}.
     *
     * @param entidad Entidad {@link InventariosEntidad} a convertir.
     * @return DTO {@link InventarioDto} con los datos de la entidad.
     
    private InventarioDto aDto(InventariosEntidad entidad) {
        InventarioDto dto = new InventarioDto();
        dto.setId(entidad.getId());
        dto.setProductoId(entidad.getProducto() != null ? entidad.getProducto().getId() : null);
        dto.setGerenteId(entidad.getGerenteId());
        dto.setCantidadContada(entidad.getCantidadContada());
        dto.setFechaRecuento(entidad.getFechaRecuento());
        dto.setObservaciones(entidad.getObservaciones());
        return dto;
    }*/
}
