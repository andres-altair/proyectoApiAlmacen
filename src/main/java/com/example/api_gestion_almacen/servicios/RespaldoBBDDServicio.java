package com.example.api_gestion_almacen.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servicio para realizar respaldos de la base de datos
 * @author Andres
 */
@Service
public class RespaldoBBDDServicio {

    private static final Logger logger = LoggerFactory.getLogger(RespaldoBBDDServicio.class);
    // Inyección de la ruta de mysqldump desde application.properties
    @Value("${respaldo.mysqldump.path:/usr/bin/mysqldump}")
    private String mysqldumpCarpeta;

    // Inyección de usuario y password de la BBDD desde application.properties
    @Value("${gestion-usuarios.datasource.username}")
    private String dbUsu;
    @Value("${gestion-usuarios.datasource.password}")
    private String dbContrasena;
    @Value("${gestion-usuarios.datasource.jdbc-url}")
    private String dbUrl;

    @Value("${gestion-almacenes.datasource.username}")
    private String almacenesUsu;
    @Value("${gestion-almacenes.datasource.password}")
    private String almacenesContrasena;
    @Value("${gestion-almacenes.datasource.jdbc-url}")
    private String almacenesUrl;

    /**
     * Exporta la estructura de la base de datos especificada
     * @param respuesta respuesta HTTP
     * @param nombreBBDD nombre de la base de datos
     */
    public void exportarEstructura(HttpServletResponse respuesta, String nombreBBDD) {
        logger.info("[exportarEstructura] Iniciando exportación de estructura para base: {}", nombreBBDD);
        try {
        respuesta.setContentType("application/sql");
        respuesta.setHeader("Content-Disposition", "attachment; filename=estructura_" + nombreBBDD + ".sql");

        String jdbcUrl;
        if ("gestion_almacenes".equals(nombreBBDD)) {
            jdbcUrl = almacenesUrl;
        } else {
            jdbcUrl = dbUrl;
        }
        logger.debug("[exportarEstructura] Conectando a la base de datos: {}", jdbcUrl);
        try (Connection con = DriverManager.getConnection(jdbcUrl, dbUsu, dbContrasena);
             PrintWriter escritor = respuesta.getWriter()) {
            // 1. Listar tablas
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            List<String> tablas = new ArrayList<>();
            while (rs.next()) {
                tablas.add(rs.getString(1));
            }
            rs.close();
            logger.debug("[exportarEstructura] Tablas encontradas: {}", tablas);

            // 2. Exportar solo estructura
            for (String tabla : tablas) {
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SHOW CREATE TABLE " + tabla);
                if (rs2.next()) {
                    escritor.println("DROP TABLE IF EXISTS " + tabla + ";");
                    escritor.println(rs2.getString(2) + ";");
                    escritor.println();
                    logger.debug("[exportarEstructura] Estructura exportada de tabla: {}", tabla);
                }
                rs2.close();
                stmt2.close();
            }
            stmt.close();
            escritor.flush();
            logger.info("[exportarEstructura] Exportación de estructura finalizada para base: {}", nombreBBDD);
        }
    } catch (Exception e) {
        logger.error("[exportarEstructura] Error al exportar estructura de la base de datos '{}': {}", nombreBBDD, e.getMessage(), e);
        throw new RuntimeException("Error al exportar estructura de la base de datos: " + e.getMessage(), e);
    }
}

    /**
     * Exporta los datos de la base de datos especificada
     * @param respuesta respuesta HTTP
     * @param nombreBBDD nombre de la base de datos
     */
    public void exportarDatos(HttpServletResponse respuesta, String nombreBBDD) {
        logger.info("[exportarDatos] Iniciando exportación de datos para base: {}", nombreBBDD);
        try {
        respuesta.setContentType("application/sql");
        respuesta.setHeader("Content-Disposition", "attachment; filename=datos_" + nombreBBDD + ".sql");

        String jdbcUrl;
        if ("gestion_almacenes".equals(nombreBBDD)) {
            jdbcUrl = almacenesUrl;
        } else {
            jdbcUrl = dbUrl;
        }
        logger.debug("[exportarDatos] Conectando a la base de datos: {}", jdbcUrl);
        try (Connection con = DriverManager.getConnection(jdbcUrl, dbUsu, dbContrasena);
             PrintWriter escritor = respuesta.getWriter()) {
            // 1. Listar tablas
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            List<String> tablas = new ArrayList<>();
            while (rs.next()) {
                tablas.add(rs.getString(1));
            }
            rs.close();
            logger.debug("[exportarDatos] Tablas encontradas: {}", tablas);

            // 2. Exportar solo datos
            for (String tabla : tablas) {
                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT * FROM " + tabla);
                ResultSetMetaData metaData = rs3.getMetaData();
                int columnas = metaData.getColumnCount();
                int filasExportadas = 0;
                while (rs3.next()) {
                    StringBuilder insertar = new StringBuilder("INSERT INTO " + tabla + " (");
                    for (int i = 1; i <= columnas; i++) {
                        insertar.append(metaData.getColumnName(i));
                        if (i < columnas) insertar.append(", ");
                    }
                    insertar.append(") VALUES (");
                    for (int i = 1; i <= columnas; i++) {
                        String valor = rs3.getString(i);
                        if (valor == null) {
                            insertar.append("NULL");
                        } else {
                            insertar.append("'").append(valor.replace("'", "''")).append("'");
                        }
                        if (i < columnas) insertar.append(", ");
                    }
                    insertar.append(");");
                    escritor.println(insertar.toString());
                    filasExportadas++;
                }
                rs3.close();
                stmt3.close();
                escritor.println();
                logger.debug("[exportarDatos] Exportadas {} filas de la tabla: {}", filasExportadas, tabla);
            }
            stmt.close();
            escritor.flush();
            logger.info("[exportarDatos] Exportación de datos finalizada para base: {}", nombreBBDD);
        }
    } catch (Exception e) {
        logger.error("[exportarDatos] Error al exportar datos de la base de datos '{}': {}", nombreBBDD, e.getMessage(), e);
        throw new RuntimeException("Error al exportar datos de la base de datos: " + e.getMessage(), e);
    }
}

    /**
     * Realiza un backup completo de ambas bases de datos usando mysqldump (ProcessBuilder) y los guarda en el servidor.
     * Si usuario o password o rutaDirectorio son null, toma los valores del properties o valores por defecto.
     * Los archivos se guardan como respaldo_gestion_usuarios.sql y respaldo_gestion_almacenes.sql
     * @param usuario usuario de la base de datos
     * @param contrasenaS contraseña de la base de datos
     * @param rutaDirectorio ruta donde se guardan los respaldos
     */
    public void respaldoCompletoAmbasBBDD(String usuario, String contrasenaS, String rutaDirectorio) {
        logger.info("[respaldoCompletoAmbasBBDD] Iniciando respaldo completo de ambas BBDD. Usuario: {}, Ruta: {}", usuario != null ? usuario : dbUsu, rutaDirectorio);
        try {
        String usu = (usuario != null) ? usuario : dbUsu;
        String contrasena = (contrasenaS != null) ? contrasenaS : dbContrasena;
        // Por defecto, ruta temporal del sistema si no se indica
        String ruta = (rutaDirectorio != null && !rutaDirectorio.isBlank()) ? rutaDirectorio : System.getProperty("java.io.tmpdir");
        // --- SOLO comprobación de ruta y permisos, NO crear directorio ---
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            logger.error("[respaldoCompletoAmbasBBDD] El directorio de respaldo no existe: {}", ruta);
            throw new IOException("El directorio de respaldo no existe: " + ruta);
        }
        if (!directorio.isDirectory()) {
            logger.error("[respaldoCompletoAmbasBBDD] La ruta de respaldo no es un directorio: {}", ruta);
            throw new IOException("La ruta de respaldo no es un directorio: " + ruta);
        }
        if (!directorio.canWrite()) {
            logger.error("[respaldoCompletoAmbasBBDD] No hay permisos de escritura en el directorio de respaldo: {}", ruta);
            throw new IOException("No hay permisos de escritura en el directorio de respaldo: " + ruta);
        }
        // --------------------------------------
        String[] bases = {"gestion_usuarios", "gestion_almacenes"};
        for (String db : bases) {
            String rutaArchivo = ruta;
            if (!rutaArchivo.endsWith(File.separator)) {
                rutaArchivo += File.separator;
            }
            rutaArchivo += "respaldo_" + db + ".sql";
            logger.debug("[respaldoCompletoAmbasBBDD] Ejecutando mysqldump para base: {}. Archivo destino: {}", db, rutaArchivo);
            ProcessBuilder pb = new ProcessBuilder(
                mysqldumpCarpeta,
                "-u" + usu,
                "-p" + contrasena,
                db,
                "-r",
                rutaArchivo
            );
            pb.redirectErrorStream(true);
            Process proceso = pb.start();
            StringBuilder salida = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    salida.append(linea).append(System.lineSeparator());
                }
            }
            int exito = proceso.waitFor();
            if (exito != 0) {
                logger.error("[respaldoCompletoAmbasBBDD] mysqldump falló para base: {}. Código: {}. Mensaje: {}", db, exito, salida.toString());
                throw new IOException("Error al ejecutar mysqldump para " + db + ". Código de salida: " + exito + ". Mensaje: " + salida.toString());
            } else {
                logger.info("[respaldoCompletoAmbasBBDD] Respaldo exitoso para base: {}. Archivo: {}", db, rutaArchivo);
            }
        }
    } catch (Exception e) {
        logger.error("[respaldoCompletoAmbasBBDD] Error al realizar respaldo completo de ambas BBDD: {}", e.getMessage(), e);
        throw new RuntimeException("Error al realizar respaldo completo de ambas BBDD: " + e.getMessage(), e);
    }
}

    // Extrae el nombre de la base de datos de la URL JDBC
    /* private String extraerNombreBBDD(String jdbcUrl) {
        // jdbc:mysql://host:puerto/nombre?params
        int barra = jdbcUrl.lastIndexOf("/");
        int interrog = jdbcUrl.indexOf("?", barra);
        if (barra == -1) return null;
        if (interrog == -1) return jdbcUrl.substring(barra + 1);
        return jdbcUrl.substring(barra + 1, interrog);
    }*/
}