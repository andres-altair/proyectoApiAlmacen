package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.RutaRespuestaDto;
import com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad;
import com.example.api_gestion_almacen.entidades.almacenes.RutasEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.PedidosRepositorio;
import com.example.api_gestion_almacen.repositorios.almacenes.RutasRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Servicio para gestionar rutas
 * @author Andres
  */
@Service
public class RutaServicio {

    @Autowired
    private PedidosRepositorio pedidosRepositorio;

    @Autowired
    private RutasRepositorio rutasRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(RutaServicio.class);

    /**
     * Obtiene las rutas activas para un transportista
     * @param transportistaId id del transportista
     * @return lista de rutas activas
     */
    public List<RutaRespuestaDto> obtenerRutasActivasPorTransportista(Long transportistaId) {
    try {
        logger.info("Servicio: obteniendo rutas activas para transportista {}", transportistaId);
        List<Object[]> filas = pedidosRepositorio.buscarPedidosConNombreProductoTransportista(
                PedidosEntidad.EstadoPedido.enviando.name(), transportistaId);
        List<Long> pedidosIds = filas.stream()
                .map(fila -> ((Number) fila[0]).longValue())
                .collect(Collectors.toList());

        List<RutasEntidad> rutas = rutasRepositorio.findByPedidoIdIn(pedidosIds);

        List<RutaRespuestaDto> rutasDto = rutas.stream().map(ruta -> {
            RutaRespuestaDto dto = new RutaRespuestaDto();
            dto.setOrigen(ruta.getOrigen());
            dto.setDestino(ruta.getDestino());
            dto.setUrlGoogleMaps(ruta.getUrlGoogleMaps());
            return dto;
        }).collect(Collectors.toList());
        logger.info("Servicio: rutas activas obtenidas para transportista {}: {} rutas", transportistaId, rutasDto.size());
        return rutasDto;
    } catch (IllegalStateException | SecurityException e) {
        logger.warn("Validación de negocio al obtener rutas activas: {}", e.getMessage());
        throw e;
    } catch (Exception e) {
        logger.error("Error al obtener rutas activas para transportista {}: {}", transportistaId, e.getMessage(), e);
        throw new RuntimeException("Error al obtener rutas activas", e);
    }
}
}

