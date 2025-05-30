package com.example.api_gestion_almacen;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * Clase que inicializa la aplicación como un servlet
 * @author Andres
 */
public class ServletInitializer extends SpringBootServletInitializer {
/**
 * Método que configura la aplicación
 * @param application la aplicación a configurar
 * @return la aplicación configurada
 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiGestionAlmacenApplication.class);
	}

}
