package com.example.api_gestion_almacen.servicios.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.PedidoCrearDto;
import com.example.api_gestion_almacen.dtos.almacenes.PedidoRespuestaDto;
import com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad;
import com.example.api_gestion_almacen.entidades.almacenes.ProductosEntidad;
import com.example.api_gestion_almacen.entidades.almacenes.RutasEntidad;
import com.example.api_gestion_almacen.repositorios.almacenes.PedidosRepositorio;
import com.example.api_gestion_almacen.repositorios.almacenes.ProductosRepositorio;
import com.example.api_gestion_almacen.repositorios.almacenes.RutasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Servicio para gestionar pedidos
 * @author Andres
 */
@Service
public class PedidosServicio {

    private static final Logger logger = LoggerFactory.getLogger(PedidosServicio.class);

    @Autowired
    private PedidosRepositorio pedidosRepositorio;

    @Autowired
    private RutasRepositorio rutasRepositorio;
    @Autowired
    private ProductosRepositorio productosRepositorio;
    /**
     * Lista todos los pedidos.
     * @author andres
     * 
     * @return List<PedidoRespuestaDto>
     */
    public List<PedidoRespuestaDto> listarPorEstado(PedidosEntidad.EstadoPedido estado) {
        try {
            logger.info("Servicio: listando pedidos por estado: {}", estado);
            List<Object[]> filas = pedidosRepositorio.buscarPedidosConNombreProducto(estado != null ? estado.name() : null);
            return filas.stream().map(this::aRespuestaDtoDesdeFilas).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al listar pedidos por estado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al listar pedidos por estado");
        }
    }
    /**
     * Lista todos los pedidos por operario y estado.
     * @author andres
     * 
     * @param operarioId Id del operario
     * @param estado Estado del pedido
     * @return List<PedidoRespuestaDto>
     */
    public List<PedidoRespuestaDto> listarPorOperarioYEstado(Long operarioId, PedidosEntidad.EstadoPedido estado) {
        try {
            logger.info("Servicio: listando pedidos por operarioId: {} y estado: {}", operarioId, estado);
            List<Object[]> filas = pedidosRepositorio.buscarPedidosConNombreProductoUsuarioPorOperario(
                estado != null ? estado.name() : null,
                operarioId
            );
            return filas.stream().map(this::aRespuestaDtoDesdeFilas).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al listar pedidos por operario y estado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al listar pedidos por operario y estado");
        }
    }
    /**
     * Lista todos los pedidos por transportista y estado.
     * @author andres
     * 
     * @param transportistaId Id del transportista
     * @param estado Estado del pedido
     * @return List<PedidoRespuestaDto>
     */
    public List<PedidoRespuestaDto> listarPorTransportistaYEstado(Long transportistaId, PedidosEntidad.EstadoPedido estado) {
        try {
            logger.info("Servicio: listando pedidos por transportistaId: {} y estado: {}", transportistaId, estado);
            List<Object[]> filas = pedidosRepositorio.buscarPedidosConNombreProductoTransportista(
                estado != null ? estado.name() : null,
                transportistaId
            );
            return filas.stream().map(this::aRespuestaDtoDesdeFilas).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al listar pedidos por transportista y estado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al listar pedidos por transportista y estado");
        }
    }
    /**
     * Asigna un operario a un pedido.
     * @author andres
     * 
     * @param pedidoId Id del pedido
     * @param operarioId Id del operario
     * @return PedidosEntidad
     */
    public PedidosEntidad asignarOperario(Long pedidoId, Long operarioId) {
        try {
            logger.info("Servicio: asignando operario {} al pedido {}", operarioId, pedidoId);
            PedidosEntidad pedido = buscarPedidoOPetardear(pedidoId);
            if (pedido.getEstadoPedido() != PedidosEntidad.EstadoPedido.pendiente) {
                throw new IllegalStateException("El pedido no está pendiente");
            }
            pedido.setEstadoPedido(PedidosEntidad.EstadoPedido.en_proceso);
            pedido.setOperarioId(operarioId);
            PedidosEntidad guardado = pedidosRepositorio.save(pedido);
            logger.info("Servicio: operario {} asignado al pedido {}", operarioId, pedidoId);
            return guardado;
        } catch (IllegalStateException | SecurityException e) {
            logger.warn("Validación de negocio al asignar operario: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al asignar operario: {}", e.getMessage(), e);
            throw new RuntimeException("Error al asignar operario");
        }
    }
    /**
     * Marca un pedido como procesado por un operario.
     * @author andres
     * 
     * @param pedidoId Id del pedido
     * @param operarioId Id del operario
     * @return PedidosEntidad
     */
    public PedidosEntidad marcarProcesado(Long pedidoId, Long operarioId) {
        try {
            logger.info("Servicio: marcando pedido {} como procesado por operario {}", pedidoId, operarioId);
            PedidosEntidad pedido = buscarPedidoOPetardear(pedidoId);
            if (pedido.getEstadoPedido() != PedidosEntidad.EstadoPedido.en_proceso) {
                throw new IllegalStateException("El pedido no está en proceso");
            }
            if (!operarioId.equals(pedido.getOperarioId())) {
                throw new SecurityException("Solo el operario asignado puede procesar el pedido");
            }
            pedido.setEstadoPedido(PedidosEntidad.EstadoPedido.procesado);
            PedidosEntidad guardado = pedidosRepositorio.save(pedido);
            logger.info("Servicio: pedido {} marcado como procesado", pedidoId);
            return guardado;
        } catch (IllegalStateException | SecurityException e) {
            logger.warn("Validación de negocio al marcar como procesado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al marcar como procesado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al marcar como procesado", e);
        }
    }
    /**
     * Asigna un transportista a un pedido.
     * @author andres
     * 
     * @param pedidoId Id del pedido
     * @param transportistaId Id del transportista
     * @return PedidosEntidad
     */
    public PedidosEntidad asignarTransportista(Long pedidoId, Long transportistaId) {
        try {
            logger.info("Servicio: asignando transportista {} al pedido {}", transportistaId, pedidoId);
            PedidosEntidad pedido = buscarPedidoOPetardear(pedidoId);
            // Permitir asignar si está en procesado o enviando
            if (pedido.getEstadoPedido() != PedidosEntidad.EstadoPedido.procesado &&
                pedido.getEstadoPedido() != PedidosEntidad.EstadoPedido.enviando) {
                throw new IllegalStateException("El pedido debe estar en estado procesado o enviando para asignar transportista");
            }
            // Si está en procesado, cambiar a enviando
            if (pedido.getEstadoPedido() == PedidosEntidad.EstadoPedido.procesado) {
                pedido.setEstadoPedido(PedidosEntidad.EstadoPedido.enviando);
            }
            pedido.setTransportistaId(transportistaId);
            PedidosEntidad guardado = pedidosRepositorio.save(pedido);
            logger.info("Servicio: transportista {} asignado al pedido {}", transportistaId, pedidoId);
            return guardado;
        } catch (IllegalStateException | SecurityException e) {
            logger.warn("Validación de negocio al asignar transportista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al asignar transportista: {}", e.getMessage(), e);
            throw new RuntimeException("Error al asignar transportista", e);
        }
    }
    /**
     * Marca un pedido como entregado por un transportista.
     * @author andres
     * 
     * @param pedidoId Id del pedido
     * @param transportistaId Id del transportista
     * @return PedidosEntidad
     */
    public PedidosEntidad marcarEntregado(Long pedidoId, Long transportistaId) {
        try {
            logger.info("Servicio: marcando pedido {} como entregado por transportista {}", pedidoId, transportistaId);
            PedidosEntidad pedido = buscarPedidoOPetardear(pedidoId);
            if (pedido.getEstadoPedido() != PedidosEntidad.EstadoPedido.enviando) {
                throw new IllegalStateException("El pedido no está en envío");
            }
            if (!transportistaId.equals(pedido.getTransportistaId())) {
                throw new SecurityException("Solo el transportista asignado puede marcar el pedido como entregado");
            }
            pedido.setEstadoPedido(PedidosEntidad.EstadoPedido.entregado);
            PedidosEntidad guardado = pedidosRepositorio.save(pedido);
            logger.info("Servicio: pedido {} marcado como entregado", pedidoId);
            return guardado;
        } catch (IllegalStateException | SecurityException e) {
            logger.warn("Validación de negocio al marcar como entregado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al marcar como entregado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al marcar como entregado", e);
        }
    }
    /**
     * Lista todos los pedidos por usuario.
     * @author andres
     * 
     * @param usuarioId Id del usuario
     * @return List<PedidoRespuestaDto>
     */
    public List<PedidoRespuestaDto> listarPorUsuario(Long usuarioId) {
        try {
            List<PedidosEntidad> pedidos = pedidosRepositorio.findByUsuarioId(usuarioId);
            if (pedidos == null ) {
                throw new RuntimeException("El usuario no tiene pedidos registrados");
            }
            return pedidos.stream().map(this::aRespuestaDto).collect(Collectors.toList());
        } catch (IllegalStateException e) {
            logger.warn("Validación de negocio al listar pedidos por usuario: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al listar pedidos por usuario: {}", e.getMessage(), e);
            throw new RuntimeException("Error al listar pedidos por usuario");
        }
    }

    /**
     * Método general: crea pedido SIN descontar stock automáticamente
     * @author andres
     * 
     * @param pedido Pedido a crear
     * @return PedidosEntidad
     */
    public PedidosEntidad crearPedido(PedidosEntidad pedido) {
        try {
            logger.info("Servicio: creando pedido usuarioId={}, productoId={}, cantidad={}",
                    pedido.getUsuarioId(), pedido.getProductoId(), pedido.getCantidad());
            pedido.setEstadoPedido(PedidosEntidad.EstadoPedido.pendiente);
            pedido.setFechaPedido(LocalDateTime.now());
            PedidosEntidad guardado = pedidosRepositorio.save(pedido);
            logger.info("Servicio: pedido guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (Exception e) {
            logger.error("Error al crear pedido: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear pedido", e);
        }
    }

    /**
     * Método específico para el endpoint ficticio: descuenta stock, crea pedido y ruta
     * @author andres
     * 
     * @param dto DTO con los datos del pedido
     * @param origen Origen de la ruta
     * @param destino Destino de la ruta
     * @return PedidosEntidad
     */
    public PedidosEntidad crearPedidoFicticio(PedidoCrearDto dto, String origen, String destino) {
        // 1. Obtener y descontar stock
        ProductosEntidad producto = productosRepositorio.findById(dto.getProductoId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        int cantidadSolicitada = dto.getCantidad() != null ? dto.getCantidad() : 1;
        if (producto.getCantidad() == null || producto.getCantidad() < cantidadSolicitada) {
            throw new IllegalArgumentException("No hay stock suficiente para el producto");
        }
        producto.setCantidad(producto.getCantidad() - cantidadSolicitada);
        productosRepositorio.save(producto);

        // 2. Crear el pedido
        PedidosEntidad entidad = crearDto(dto);
        PedidosEntidad creado = crearPedido(entidad);

        // 3. Crear la ruta asociada
        crearRutaParaPedido(creado.getId(), origen, destino);

        return creado;
    }

    /**
     * Crea una ruta para un pedido.
     * @author andres
     * 
     * @param pedidoId Id del pedido
     * @param origen Origen de la ruta
     * @param destino Destino de la ruta
     */
    public void crearRutaParaPedido(Long pedidoId, String origen, String destino) {
        String origenUrl = origen.replace(" ", "+");
        String destinoUrl = destino.replace(" ", "+");
        String urlGoogleMaps = "https://www.google.com/maps/dir/?api=1&origin=" + origenUrl + "&destination=" + destinoUrl;

        RutasEntidad ruta = new RutasEntidad();
        ruta.setPedidoId(pedidoId);
        ruta.setOrigen(origen);
        ruta.setDestino(destino);
        ruta.setUrlGoogleMaps(urlGoogleMaps);
        rutasRepositorio.save(ruta);
    }

    /**
     * Convierte un PedidoCrearDto a PedidosEntidad.
     * @author andres
     * 
     * @param dto DTO con los datos del pedido
     * @return PedidosEntidad
     */
    public PedidosEntidad crearDto(PedidoCrearDto dto) {
        try {
            PedidosEntidad entidad = new PedidosEntidad();
            entidad.setUsuarioId(dto.getUsuarioId());
            entidad.setProductoId(dto.getProductoId());
            entidad.setCantidad(dto.getCantidad());
            return entidad;
        } catch (Exception e) {
            logger.error("Error al convertir DTO a entidad: {}", e.getMessage(), e);
            throw new RuntimeException("Error al convertir DTO a entidad", e);
        }
    }

    /**
     * Convierte un PedidosEntidad a PedidoRespuestaDto.
     * @author andres
     * 
     * @param entidad Entidad del pedido
     * @return PedidoRespuestaDto
     */
    public PedidoRespuestaDto aRespuestaDto(PedidosEntidad entidad) {
        try {
            PedidoRespuestaDto dto = new PedidoRespuestaDto();
            dto.setId(entidad.getId());
            dto.setUsuarioId(entidad.getUsuarioId());
            dto.setProductoId(entidad.getProductoId());
            dto.setCantidad(entidad.getCantidad());
            dto.setEstadoPedido(entidad.getEstadoPedido().name());
            dto.setOperarioId(entidad.getOperarioId());
            dto.setTransportistaId(entidad.getTransportistaId());
            dto.setFechaPedido(entidad.getFechaPedido());
            return dto;
        } catch (Exception e) {
            logger.error("Error al convertir entidad a DTO: {}", e.getMessage(), e);
            throw new RuntimeException("Error al convertir entidad a DTO", e);
        }
    }

    

    /**
     * Convierte un array de Object a PedidoRespuestaDto.
     * @author andres
     * 
     * @param fila Array de Object con los datos del pedido
     * @return PedidoRespuestaDto
     */
    private PedidoRespuestaDto aRespuestaDtoDesdeFilas(Object[] fila) {
    PedidoRespuestaDto dto = new PedidoRespuestaDto();
    dto.setId(((Number) fila[0]).longValue());
    dto.setUsuarioId(((Number) fila[1]).longValue());
    dto.setProductoId(((Number) fila[2]).longValue());
    dto.setCantidad(((Number) fila[3]).intValue());
    dto.setEstadoPedido(fila[4] != null ? fila[4].toString() : null);
    dto.setOperarioId(fila[5] != null ? ((Number) fila[5]).longValue() : null);
    dto.setTransportistaId(fila[6] != null ? ((Number) fila[6]).longValue() : null);
    dto.setFechaPedido(fila[7] != null ? ((java.sql.Timestamp) fila[7]).toLocalDateTime() : null);
    dto.setNombreProducto(fila[8] != null ? fila[8].toString() : null);
    return dto;
    }

    /**
     * Busca un pedido por su ID.
     * @author andres
     * 
     * @param id Id del pedido
     * @return PedidosEntidad
     */
    private PedidosEntidad buscarPedidoOPetardear(Long id) {
        try {
            Optional<PedidosEntidad> pedidoOpt = pedidosRepositorio.findById(id);
            if (pedidoOpt.isEmpty()) {
                throw new RuntimeException("Pedido no encontrado");
            }
            return pedidoOpt.get();
        } catch (Exception e) {
            logger.error("Error al buscar pedido: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar pedido", e);
        }
    }







    /**
     * Lista todos los pedidos pendientes.
     * @author andres
     * 
     * @return List<PedidoRespuestaDto>
    
    public List<PedidoRespuestaDto> listarPedidosPendientes() {
        return listarPorEstado(PedidosEntidad.EstadoPedido.pendiente);
    } */
}