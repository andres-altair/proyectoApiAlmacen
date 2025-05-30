package com.example.api_gestion_almacen.controladores.almacenes;

import com.example.api_gestion_almacen.dtos.almacenes.AsignacionUsuarioDto;
import com.example.api_gestion_almacen.dtos.almacenes.PedidoCrearDto;
import com.example.api_gestion_almacen.dtos.almacenes.PedidoRespuestaDto;
import com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad;
import com.example.api_gestion_almacen.servicios.almacenes.PedidosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** 
 * Controlador para gestionar los pedidos.
 * @author andres
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidosControlador {

    private static final Logger logger = LoggerFactory.getLogger(PedidosControlador.class);

    @Autowired
    private PedidosServicio pedidosServicio;

    /**
     * Lista todos los pedidos.
     * @author andres
     * 
     * @param usuarioId Id del usuario
     * @param operarioId Id del operario
     * @param transportistaId Id del transportista
     * @param estado Estado del pedido
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listarPedidos(
            @RequestParam(name = "usuarioId", required = false) Long usuarioId,
            @RequestParam(name = "operarioId", required = false) Long operarioId,
            @RequestParam(name = "transportistaId", required = false) Long transportistaId,
            @RequestParam(name = "estado", required = false) PedidosEntidad.EstadoPedido estado) {
        try {
            logger.info("Listando pedidos. usuarioId={}, operarioId={}, transportistaId={}, estado={}", usuarioId, operarioId, transportistaId, estado);
            if (operarioId != null && estado != null) {
                return ResponseEntity.ok(pedidosServicio.listarPorOperarioYEstado(operarioId, estado));
            } else if (transportistaId != null && estado != null) {
                return ResponseEntity.ok(pedidosServicio.listarPorTransportistaYEstado(transportistaId, estado));
            } else if (estado != null) {
                return ResponseEntity.ok(pedidosServicio.listarPorEstado(estado));
            } 
            else if (usuarioId != null) {
                return ResponseEntity.ok(pedidosServicio.listarPorUsuario(usuarioId));
            } else {
                return ResponseEntity.badRequest().body("Se requiere el parámetro 'estado' y uno de 'operarioId' o 'transportistaId'.");
            }
        } catch (Exception e) {
            logger.error("Error al listar pedidos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar pedidos: " + e.getMessage());
        }
    }
    
    /**
     * Asigna un operario a un pedido.
     * @author andres
     * 
     * @param id Id del pedido
     * @param pedido DTO con el id del operario
     * @return ResponseEntity<?>
     */
    @PutMapping("/{id}/asignar-operario")
    public ResponseEntity<?> asignarOperario(@PathVariable Long id, @RequestBody AsignacionUsuarioDto pedido) {
        try {
            logger.info("Asignando operario {} al pedido {}", pedido.getUsuarioId(), id);
            PedidosEntidad actualizado = pedidosServicio.asignarOperario(id, pedido.getUsuarioId());
            logger.info("Operario asignado correctamente al pedido {}", id);
            return ResponseEntity.ok(pedidosServicio.aRespuestaDto(actualizado));
        } catch (Exception e) {
            logger.error("Error al asignar operario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al asignar operario: " + e.getMessage());
        }
    }
    
    /**
     * Marca un pedido como procesado.
     * @author andres
     * 
     * @param id Id del pedido
     * @param pedido DTO con el id del operario
     * @return ResponseEntity<?>
     */
    @PutMapping("/{id}/procesar")
    public ResponseEntity<?> marcarProcesado(@PathVariable Long id, @RequestBody AsignacionUsuarioDto pedido) {
        try {
            logger.info("Marcando pedido {} como procesado por operario {}", id, pedido.getUsuarioId());
            PedidosEntidad actualizado = pedidosServicio.marcarProcesado(id, pedido.getUsuarioId());
            logger.info("Pedido {} marcado como procesado", id);
            return ResponseEntity.ok(pedidosServicio.aRespuestaDto(actualizado));
        } catch (Exception e) {
            logger.error("Error al marcar como procesado: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al marcar como procesado: " + e.getMessage());
        }
    }
    
    /**
     * Asigna un transportista a un pedido.
     * @author andres
     * 
     * @param id Id del pedido
     * @param pedido DTO con el id del transportista
     * @return ResponseEntity<?>
     */
    @PutMapping("/{id}/asignar-transportista")
    public ResponseEntity<?> asignarTransportista(@PathVariable Long id, @RequestBody AsignacionUsuarioDto pedido) {
        try {
            logger.info("Asignando transportista {} al pedido {}", pedido.getUsuarioId(), id);
            PedidosEntidad actualizado = pedidosServicio.asignarTransportista(id, pedido.getUsuarioId());
            logger.info("Transportista asignado correctamente al pedido {}", id);
            return ResponseEntity.ok(pedidosServicio.aRespuestaDto(actualizado));
        } catch (Exception e) {
            logger.error("Error al asignar transportista: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al asignar transportista: " + e.getMessage());
        }
    }   
    
    /**
     * Marca un pedido como entregado.
     * @author andres
     * 
     * @param id Id del pedido
     * @param pedido DTO con el id del transportista
     * @return ResponseEntity<?>
     */
    @PutMapping("/{id}/entregar")
    public ResponseEntity<?> marcarEntregado(@PathVariable Long id, @RequestBody AsignacionUsuarioDto pedido) {
        try {
            logger.info("Marcando pedido {} como entregado por transportista {}", id, pedido.getUsuarioId());
            PedidosEntidad actualizado = pedidosServicio.marcarEntregado(id, pedido.getUsuarioId());
            logger.info("Pedido {} marcado como entregado", id);
            return ResponseEntity.ok(pedidosServicio.aRespuestaDto(actualizado));
        } catch (Exception e) {
            logger.error("Error al marcar como entregado: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al marcar como entregado: " + e.getMessage());
        }
    }

    /**
     * Crea un pedido ficticio para pruebas.
     * @author andres
     * 
     * @param destino Dirección de destino
     * @param cantidad Cantidad de productos
     * @return ResponseEntity<?>
     */
    @PostMapping("/ficticio")
public ResponseEntity<?> crearPedidoFicticio(@RequestParam String destino, @RequestParam(required = false, defaultValue = "1") Integer cantidad) {
    try {
        logger.info("Creando pedido ficticio para pruebas");
        PedidoCrearDto dto = new PedidoCrearDto();
        dto.setUsuarioId(5L); // O el ID que corresponda
        dto.setProductoId(1L); // O el ID que corresponda
        dto.setCantidad(cantidad);
        dto.setEstadoPedido(PedidosEntidad.EstadoPedido.pendiente);

        String origen = "Av Siempre Viva 742 Springfield"; // <-- cambia por tu dirección real

        PedidosEntidad creado = pedidosServicio.crearPedidoFicticio(dto, origen, destino);

        PedidoRespuestaDto respuesta = pedidosServicio.aRespuestaDto(creado);
        logger.info("Pedido ficticio creado con ID: {}", respuesta.getId());
        return ResponseEntity.ok(respuesta);
    } catch (IllegalArgumentException e) {
        logger.error("Error de validación: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        logger.error("Error al crear pedido ficticio: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear pedido ficticio: " + e.getMessage());
    }
}
/**
 * Crea un pedido ficticio de camiseta con direcciones reales en España.
 * @author andres
 * 
 * @param cantidad Cantidad de productos
 * @return ResponseEntity<?>
 */
    @PostMapping("/ficticio-camiseta")
public ResponseEntity<?> crearPedidoFicticioCamiseta(@RequestParam(required = false, defaultValue = "1") Integer cantidad) {
    try {
        logger.info("Creando pedido ficticio de camiseta con direcciones reales en España");
        PedidoCrearDto dto = new PedidoCrearDto();
        dto.setUsuarioId(5L); // ID usuario
        dto.setProductoId(2L); // ID producto
        dto.setCantidad(cantidad);
        dto.setEstadoPedido(PedidosEntidad.EstadoPedido.pendiente);

        String origen = "Plaza del Pilar, Zaragoza, España";
        String destino = "Calle Larios 1, Málaga, España";

        PedidosEntidad creado = pedidosServicio.crearPedidoFicticio(dto, origen, destino);

        PedidoRespuestaDto respuesta = pedidosServicio.aRespuestaDto(creado);
        logger.info("Pedido ficticio de camiseta creado con ID: {}", respuesta.getId());
        return ResponseEntity.ok(respuesta);
    } catch (IllegalArgumentException e) {
        logger.error("Error de validación: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        logger.error("Error al crear pedido ficticio de camiseta: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al crear pedido ficticio de camiseta: " + e.getMessage());
    }
}
/**
 * Crea un pedido ficticio para un nuevo producto con direcciones reales en Madrid.
 * @author andres
 * 
 * @param cantidad Cantidad de productos
 * @return ResponseEntity<?>
 */
@PostMapping("/ficticio-nuevo-producto")
public ResponseEntity<?> crearPedidoFicticioNuevoProducto(@RequestParam(required = false, defaultValue = "2") Integer cantidad) {
    try {
        logger.info("Creando pedido ficticio para nuevo producto con direcciones reales en Madrid");
        PedidoCrearDto dto = new PedidoCrearDto();
        dto.setUsuarioId(5L); // ID de usuario, ajusta si es necesario
        dto.setProductoId(3L); // Cambia esto si el ID del nuevo producto es diferente
        dto.setCantidad(cantidad);
        dto.setEstadoPedido(PedidosEntidad.EstadoPedido.pendiente);

        String origen = "Passeig de Gràcia 1, Barcelona, España";
        String destino = "Avinguda del Port 1, Valencia, España";

        PedidosEntidad creado = pedidosServicio.crearPedidoFicticio(dto, origen, destino);

        PedidoRespuestaDto respuesta = pedidosServicio.aRespuestaDto(creado);
        logger.info("Pedido ficticio para nuevo producto creado con ID: {}", respuesta.getId());
        return ResponseEntity.ok(respuesta);
    } catch (IllegalArgumentException e) {
        logger.error("Error de validación: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        logger.error("Error al crear pedido ficticio para nuevo producto: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al crear pedido ficticio para nuevo producto: " + e.getMessage());
    }
}
/**
 * Crea un pedido ficticio con direcciones fijas (Gran Vía y Alcalá, Madrid).
 * @author andres
 * 
 * @param cantidad Cantidad de productos
 * @return ResponseEntity<?>
 */
@PostMapping("/ficticio-direcciones-fijas")
public ResponseEntity<?> crearPedidoFicticioDireccionesFijas(@RequestParam(required = false, defaultValue = "1") Integer cantidad) {
    try {
        logger.info("Creando pedido ficticio con direcciones fijas (Gran Vía y Alcalá, Madrid)");
        PedidoCrearDto dto = new PedidoCrearDto();
        dto.setUsuarioId(5L); // Ajusta el ID según tu lógica de pruebas
        dto.setProductoId(1L); // Ajusta el producto según tu lógica de pruebas
        dto.setCantidad(cantidad);
        dto.setEstadoPedido(PedidosEntidad.EstadoPedido.pendiente);

        String origen = "Calle Gran Vía 1, Madrid, España";
        String destino = "Calle Alcalá 50, Madrid, España";

        PedidosEntidad creado = pedidosServicio.crearPedidoFicticio(dto, origen, destino);

        PedidoRespuestaDto respuesta = pedidosServicio.aRespuestaDto(creado);
        logger.info("Pedido ficticio creado con ID: {}", respuesta.getId());
        return ResponseEntity.ok(respuesta);
    } catch (IllegalArgumentException e) {
        logger.error("Error de validación: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        logger.error("Error al crear pedido ficticio con direcciones fijas: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al crear pedido ficticio con direcciones fijas: " + e.getMessage());
    }
}



    /** 
    
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoCrearDto dto) {
        try {
            logger.info("Creando pedido con datos: {}", dto);
            PedidosEntidad entidad = pedidosServicio.crearDto(dto);
            PedidosEntidad creado = pedidosServicio.crearPedido(entidad);
            PedidoRespuestaDto respuesta = pedidosServicio.aRespuestaDto(creado);
            logger.info("Pedido creado exitosamente con ID: {}", respuesta.getId());
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error al crear pedido: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear pedido: " + e.getMessage());
        }
    }

@GetMapping("/pendientes")
public ResponseEntity<?> listarPedidosPendientes() {
    try {
        return ResponseEntity.ok(
            pedidosServicio.listarPorEstado(com.example.api_gestion_almacen.entidades.almacenes.PedidosEntidad.EstadoPedido.pendiente)
        );
    } catch (Exception e) {
        logger.error("Error al listar pedidos pendientes: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al listar pedidos pendientes: " + e.getMessage());
    }
}*/












}