package com.example.api_gestion_almacen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Configuración de CORS
 * @author Andres
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String permitidos;
    /**
     * Configura CORS para permitir solicitudes desde los orígenes permitidos
     * @param registro     registro de CORS
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registro) {
        String[] origenesPermitidos = permitidos.split(",");
        registro.addMapping("/api/**")
                .allowedOrigins(origenesPermitidos)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Access-Control-Allow-Origin")
                .allowCredentials(true)
                .maxAge(3600); // para  1 hora
    }
}