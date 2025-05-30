package com.example.api_gestion_almacen.controladores;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_gestion_almacen.dtos.CrearUsuDto;
import com.example.api_gestion_almacen.dtos.PaginacionDto;
import com.example.api_gestion_almacen.dtos.UsuarioDto;
import com.example.api_gestion_almacen.servicios.UsuarioServicio;

/**
 * Controlador REST para gestionar operaciones relacionadas con usuarios.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar usuarios,
 * así como para autenticar usuarios.
 * 
 * @author Andrés
 */
@RestController
@RequestMapping("api/usuarios")
public class UsuarioControlador {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    @Autowired
    private UsuarioServicio usuarioServicio; // Servicio para manejar la lógica de negocio relacionada con usuarios


        /**
     * Busca un usuario por su correo electrónico.
     * @author andres
     *
     * @param email El correo electrónico del usuario a buscar
     * @return El usuario correspondiente al correo proporcionado
     */
    @GetMapping(path = "/correo/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorCorreo(@PathVariable String email) {
        try {
            logger.info("Buscando usuario por correo: {}", email);
            
            UsuarioDto usuario = usuarioServicio.buscarPorCorreoElectronico(email);
            
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            
            logger.info("Usuario encontrado: {}", usuario.getCorreoElectronico());
            return ResponseEntity.ok(usuario);
            
        } catch (Exception e) {
            logger.error("Error al buscar usuario por correo {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al buscar usuario: "));
        }
    }   
    
    /**
     * Autentica a un usuario utilizando sus credenciales.
     * @author andres
     *
     * @param credenciales Un mapa que contiene el correo electrónico y la contraseña del usuario.
     * @return El usuario autenticado si las credenciales son válidas.
     */
    @PostMapping(path = "/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> autenticarUsuario(@RequestBody Map<String, String> credenciales) {
        try {
            String correoElectronico = credenciales.get("correoElectronico");
            String contrasena = credenciales.get("contrasena");
            
            if (correoElectronico == null || correoElectronico.isEmpty() || contrasena == null || contrasena.isEmpty()) {
                logger.warn("Intento de autenticación con credenciales incompletas");
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Correo electrónico y contraseña son requeridos"));
            }
            
            UsuarioDto usuarioAutenticado = usuarioServicio.autenticarUsuario(correoElectronico, contrasena);
            logger.info("Autenticación exitosa para: {}", correoElectronico);
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (RuntimeException e) {
            logger.error("Error de autenticación: {}", e.getMessage());
            return ResponseEntity.status(401)
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error inesperado durante la autenticación: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor"));
        }
    }

    /**
     * Confirma el correo electrónico de un usuario.
     * @author andres
     *
     * @param email El correo electrónico del usuario a confirmar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/confirmarCorreo/{email}")
    public ResponseEntity<?> confirmarCorreo(@PathVariable String email) {
        try {
            logger.info("Iniciando confirmación de correo para: {}", email);
            usuarioServicio.confirmarCorreoUsuario(email);
            logger.info("Correo confirmado exitosamente para: {}", email);
            return ResponseEntity.ok().body(Map.of("mensaje", "Correo confirmado exitosamente"));
        } catch (Exception e) {
            logger.error("Error al confirmar correo para {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al confirmar correo: " + e.getMessage()));
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     * @author andres
     *
     * @param datos Un mapa que contiene el correo electrónico y la nueva contraseña del usuario.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/actualizarContrasena")
    public ResponseEntity<?> actualizarContrasena(@RequestBody Map<String, String> datos) {
        try {
        String correoElectronico = datos.get("correoElectronico");
        String nuevaContrasena = datos.get("nuevaContrasena");
        logger.info("Iniciando actualización de contraseña para usuario: {}", correoElectronico);

        if (correoElectronico == null || nuevaContrasena == null) {
            logger.warn("Intento de actualización de contraseña con datos incompletos");
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Correo electrónico y nueva contraseña son requeridos"));
        }

        usuarioServicio.actualizarContrasenaUsuario(correoElectronico, nuevaContrasena);
        logger.info("Contraseña actualizada exitosamente para: {}", correoElectronico);
        return ResponseEntity.ok()
            .body(Map.of("mensaje", "Contraseña actualizada exitosamente"));

    } catch (RuntimeException e) {
        logger.error("Error al actualizar contraseña: {}", e.getMessage(), e);
        String mensaje = e.getMessage();
        if ("Usuario no encontrado".equals(mensaje)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", mensaje));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", mensaje != null ? mensaje : "Error al actualizar contraseña"));
    } catch (Exception e) {
        logger.error("Error inesperado al actualizar contraseña: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Error interno del servidor"));
    }
}

    /**
     * Crea un nuevo usuario.
     * @author andres
     *
     * @param usuarioDTO El objeto que contiene la información del usuario a crear.
     * @return El usuario creado.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuDto crearUsuDTO) {
    try {
        CrearUsuDto nuevoUsuario = usuarioServicio.crearUsuario(crearUsuDTO);
        logger.info("Usuario creado exitosamente: {}", nuevoUsuario.getCorreoElectronico());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
        logger.error("Error al crear usuario: {}", e.getMessage(), e);
        String mensaje = e.getMessage();
        if (mensaje != null && mensaje.contains("correo electrónico ya está registrado")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", mensaje));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", mensaje != null ? mensaje : "Error al crear usuario"));
        } catch (Exception e) {
        logger.error("Error inesperado al crear usuario: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Error interno del servidor"));
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * @author andres
     *
     * @param id El ID del usuario a obtener.
     * @return El usuario correspondiente al ID proporcionado.
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDto obtenerUsuarioPorId(@PathVariable Long id) { 
        logger.info("Obteniendo usuario con ID: {}", id);
        return usuarioServicio.obtenerUsuarioPorId(id); 
    }

    /**
     * Obtiene todos los usuarios.
     * @author andres
     *
     * @return Una lista de todos los usuarios disponibles.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUsuariosPaginados(
        @RequestParam(defaultValue = "0") int pagina,
        @RequestParam(defaultValue = "10") int tamanio) {
        try {
            Page<UsuarioDto> usuariosPaginados = usuarioServicio.obtenerUsuariosPaginados(pagina, tamanio);
            PaginacionDto<UsuarioDto> dto = new PaginacionDto<>();
            dto.setContenido(usuariosPaginados.getContent());
            dto.setTotalPaginas(usuariosPaginados.getTotalPages());
            dto.setTotalElementos(usuariosPaginados.getTotalElements());
            dto.setNumero(usuariosPaginados.getNumber());
            dto.setTamanio(usuariosPaginados.getSize());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            logger.error("Error al obtener usuarios paginados: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener usuarios: " + e.getMessage()));
        }
    }

 

    /**
     * Actualiza un usuario existente.
     * @author andres
     *
     * @param id El ID del usuario a actualizar.
     * @param usuarioDTO El objeto que contiene la nueva información del usuario.
     * @return El usuario actualizado.
     */
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody CrearUsuDto usuarioDTO) {
        try {
        CrearUsuDto usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuarioDTO);
        logger.info("Usuario actualizado exitosamente. ID: {}", id);
        return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
        logger.error("Error al actualizar usuario {}: {}", id, e.getMessage(), e);
        String mensaje = e.getMessage();
        if (mensaje != null) {
            if (mensaje.contains("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", mensaje));
            } else if (mensaje.contains("correo electrónico ya está registrado")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", mensaje));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", mensaje != null ? mensaje : "Error al actualizar usuario"));
        } catch (Exception e) {
        logger.error("Error inesperado al actualizar usuario {}: {}", id, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor"));
        }
    }


    /**
     * @author andres
     *
     * Elimina un usuario por su ID.
     * @param id El ID del usuario a eliminar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            logger.info("Iniciando eliminación de usuario con ID: {}", id);
            usuarioServicio.eliminarUsuario(id);
            logger.info("Usuario eliminado exitosamente. ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al eliminar usuario {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + e.getMessage());
        }
    }
    
    /**
     * @author andres
     *
     * Actualiza parcialmente el perfil del usuario (nombreCompleto, movil, foto). Todos los campos son opcionales.
     * @param id El ID del usuario a actualizar.
     * @param campos Un mapa con los campos a actualizar.
     * @return El usuario actualizado.
     */
    @PutMapping(path = "/{id}/perfil", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPerfilUsuario(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        try {
        logger.info("Actualizando perfil parcial de usuario con ID: {}. Campos: {}", id, campos.keySet());
        UsuarioDto usuarioActualizado = usuarioServicio.actualizarPerfilParcial(id, campos);
        logger.info("Perfil actualizado correctamente para usuario ID: {}", id);
        return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
        logger.error("Error al actualizar perfil de usuario {}: {}", id, e.getMessage(), e);
        String mensaje = e.getMessage();
        if (mensaje != null && mensaje.contains("Usuario no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", mensaje));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", mensaje != null ? mensaje : "Error al actualizar perfil parcial"));
        } catch (Exception e) {
        logger.error("Error inesperado al actualizar perfil de usuario {}: {}", id, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor"));
        }
        }
}