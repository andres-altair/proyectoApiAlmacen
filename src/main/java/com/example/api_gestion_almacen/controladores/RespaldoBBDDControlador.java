package com.example.api_gestion_almacen.controladores;

import com.example.api_gestion_almacen.servicios.RespaldoBBDDServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Controlador para gestionar respaldos de la base de datos
 * @author Andres
  */
@RestController
@RequestMapping("/api")
public class RespaldoBBDDControlador {

    @Autowired
    private RespaldoBBDDServicio respaldoServicio;
    private static final Logger logger = LoggerFactory.getLogger(RespaldoBBDDControlador.class);

    /**
     * Endpoint para descargar solo la estructura de la base de datos especificada
     * @param respuesta respuesta HTTP
     * @param nombreBBDD nombre de la base de datos
     */
    @GetMapping("/exportar/estructura")
    public void exportarEstructura(HttpServletResponse respuesta, @RequestParam(defaultValue = "gestion_usuarios") String nombreBBDD) {
        logger.info("[exportarEstructura] Solicitud de exportación de estructura para base: {}", nombreBBDD);
        try {
            respaldoServicio.exportarEstructura(respuesta, nombreBBDD);
            logger.info("[exportarEstructura] Exportación de estructura completada para base: {}", nombreBBDD);
        } catch (RuntimeException e) {
            logger.error("[exportarEstructura] Error de negocio al exportar estructura para base '{}': {}", nombreBBDD, e.getMessage(), e);
            respuesta.setStatus(500);
            respuesta.setContentType("application/json");
            try {
                respuesta.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
            } catch (Exception io) {
                logger.error("[exportarEstructura] Error al escribir la respuesta de error para base '{}': {}", nombreBBDD, io.getMessage(), io);
            }
        } catch (Exception e) {
            logger.error("[exportarEstructura] Error inesperado al exportar estructura para base '{}': {}", nombreBBDD, e.getMessage(), e);
            respuesta.setStatus(500);
            respuesta.setContentType("application/json");
            try {
                respuesta.getWriter().write("{\"error\":\"Error interno del servidor\"}");
            } catch (Exception io) {
                logger.error("[exportarEstructura] Error al escribir la respuesta de error genérica para base '{}': {}", nombreBBDD, io.getMessage(), io);
            }
        }
    }

    /**
     * Endpoint para descargar solo los datos de la base de datos especificada
     * @param respuesta respuesta HTTP
     * @param nombreBBDD nombre de la base de datos
     */
    @GetMapping("/exportar/datos")
    public void exportarDatos(HttpServletResponse respuesta, @RequestParam(defaultValue = "gestion_usuarios") String nombreBBDD) {
        logger.info("[exportarDatos] Solicitud de exportación de datos para base: {}", nombreBBDD);
        try {
            respaldoServicio.exportarDatos(respuesta, nombreBBDD);
            logger.info("[exportarDatos] Exportación de datos completada para base: {}", nombreBBDD);
        } catch (RuntimeException e) {
            logger.error("[exportarDatos] Error de negocio al exportar datos para base '{}': {}", nombreBBDD, e.getMessage(), e);
            respuesta.setStatus(500);
            respuesta.setContentType("application/json");
            try {
                respuesta.getWriter().write("{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
            } catch (Exception io) {
                logger.error("[exportarDatos] Error al escribir la respuesta de error para base '{}': {}", nombreBBDD, io.getMessage(), io);
            }
        } catch (Exception e) {
            logger.error("[exportarDatos] Error inesperado al exportar datos para base '{}': {}", nombreBBDD, e.getMessage(), e);
            respuesta.setStatus(500);
            respuesta.setContentType("application/json");
            try {
                respuesta.getWriter().write("{\"error\":\"Error interno del servidor\"}");
            } catch (Exception io) {
                logger.error("[exportarDatos] Error al escribir la respuesta de error genérica para base '{}': {}", nombreBBDD, io.getMessage(), io);
            }
        }
    }

    /**
     * Endpoint para respaldo completo de ambas bases de datos usando mysqldump (sin parámetros, usa configuración interna)
     * @return ResponseEntity<String> respuesta HTTP con el resultado del respaldo
     */
    @PostMapping("/respaldo/completo/ambas")
    public ResponseEntity<String> respaldoCompletoAmbas() {
        logger.info("[respaldoCompletoAmbas] Solicitud de respaldo completo de ambas BBDD");
        try {
            respaldoServicio.respaldoCompletoAmbasBBDD(null, null, null); // El servicio usará los valores por defecto/configuración
            logger.info("[respaldoCompletoAmbas] Respaldo completo realizado correctamente");
            return ResponseEntity.ok("Respaldo de ambas bases guardado correctamente");
        } catch (Exception e) {
            logger.error("[respaldoCompletoAmbas] Error al realizar el respaldo completo: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error al realizar el respaldo: " + e.getMessage());
        }
    }
}