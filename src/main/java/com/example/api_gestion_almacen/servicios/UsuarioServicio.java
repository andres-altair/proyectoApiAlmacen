package com.example.api_gestion_almacen.servicios;

import java.util.Optional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.api_gestion_almacen.dtos.CrearUsuDto;
import com.example.api_gestion_almacen.dtos.UsuarioDto;
import com.example.api_gestion_almacen.entidades.usuarios.RolEntidad;
import com.example.api_gestion_almacen.entidades.usuarios.UsuarioEntidad;
import com.example.api_gestion_almacen.repositorios.usuarios.RolRepositorio;
import com.example.api_gestion_almacen.repositorios.usuarios.UsuarioRepositorio;
    import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona métodos para crear, obtener, actualizar, eliminar y autenticar usuarios.
 * @author andres
 */
@Service
public class UsuarioServicio {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Repositorio para acceder a los datos de usuarios
    
    @Autowired
    private RolRepositorio rolRepositorio;

    /**
     * Crea un nuevo usuario a partir de un UsuarioDto.
     * @author andres
     * 
     * @param usuarioDTO El objeto DTO que contiene la información del usuario a crear.
     * @return El objeto UsuarioDto del usuario creado.
     */
    public CrearUsuDto crearUsuario(CrearUsuDto crearUsuDTO) {
        
        // Verificar si el correo ya existe
        if (usuarioRepositorio.findByCorreoElectronico(crearUsuDTO.getCorreoElectronico()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        try {
            // 1. Convertir DTO a entidad
            UsuarioEntidad usuarioEntidad = aEntidad2(crearUsuDTO);
            
            // 2. Guardar en base de datos
            UsuarioEntidad usuarioGuardado = usuarioRepositorio.save(usuarioEntidad);
            
            // 3. Convertir entidad guardada a DTO
            CrearUsuDto usuarioCreado = new CrearUsuDto();
            usuarioCreado= aDto2(usuarioGuardado);
            
            return usuarioCreado;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear usuario" +e.getMessage(),e);
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * @author andres
     * 
     * @param id El ID del usuario a obtener.
     * @return El objeto UsuarioDto correspondiente al usuario encontrado, o null si no se encuentra.
     */
    public UsuarioDto obtenerUsuarioPorId(Long id) {
        try {
            UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id).orElseThrow(() -> {     
                logger.error("Usuario no encontrado con ID: {}", id);
                return new RuntimeException("Usuario no encontrado");
            });
            return aDto(usuarioEntidad);
        } catch (Exception e) {
            logger.error("Error al obtener usuario por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al obtener usuario");
        }
    }

    /**
     * Obtiene todos los usuarios disponibles.
     * @author andres
     * 
     * @return Una lista de objetos UsuarioDto que representan todos los usuarios.
     */


public Page<UsuarioDto> obtenerUsuariosPaginados(int pagina, int tamano) {
    long start = System.currentTimeMillis();
    try {
        Pageable pageable = PageRequest.of(pagina, tamano);

        long antesQuery = System.currentTimeMillis();
        Page<UsuarioEntidad> usuariosPage = usuarioRepositorio.findAll(pageable);
        long despuesQuery = System.currentTimeMillis();

        Page<UsuarioDto> usuariosDtoPage = usuariosPage.map(usuarioEntidad -> {
            UsuarioDto dto = new UsuarioDto();
            dto.setId(usuarioEntidad.getId());
            dto.setNombreCompleto(usuarioEntidad.getNombreCompleto());
            dto.setMovil(usuarioEntidad.getMovil());
            dto.setCorreoElectronico(usuarioEntidad.getCorreoElectronico());
            dto.setRolId(usuarioEntidad.getRol().getId());
            dto.setFechaCreacion(usuarioEntidad.getFechaCreacion());
            dto.setCorreoConfirmado(usuarioEntidad.isCorreoConfirmado());
            dto.setGoogle(usuarioEntidad.isGoogle());
            // Generar miniatura y convertir a base64
            if (usuarioEntidad.getFoto() != null && usuarioEntidad.getFoto().length > 0) {
                try {
                    byte[] miniatura = generarMiniatura(usuarioEntidad.getFoto(), 50, 50);
                    String base64Miniatura = java.util.Base64.getEncoder().encodeToString(miniatura);
                    dto.setFotoBase64(base64Miniatura);
                    dto.setFoto(null); // O asigna el original si lo necesitas
                } catch (Exception ex) {
                    logger.error("Error generando miniatura para usuario {}: {}", usuarioEntidad.getId(), ex.getMessage(), ex);
                    dto.setFotoBase64(null);
                    dto.setFoto(null);
                }
            } else {
                dto.setFotoBase64(null);
                dto.setFoto(null);
            }
            return dto;
        });
        long despuesDto = System.currentTimeMillis();

        logger.info("TIEMPO - Consulta BD: {} ms", (despuesQuery - antesQuery));
        logger.info("TIEMPO - Transformación a DTO: {} ms", (despuesDto - despuesQuery));
        logger.info("TIEMPO - Total método: {} ms", (despuesDto - start));

        return usuariosDtoPage;
    } catch (Exception e) {
        logger.error("Error al obtener usuarios paginados: {}", e.getMessage(), e);
        throw new RuntimeException("Error al obtener usuarios paginados");
    }
}

private byte[] generarMiniatura(byte[] imagenOriginal, int ancho, int alto) throws Exception {
    java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(imagenOriginal);
    java.awt.image.BufferedImage imagen = javax.imageio.ImageIO.read(bais);
    java.awt.Image escalada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
    java.awt.image.BufferedImage miniatura = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_INT_RGB);
    java.awt.Graphics2D g2d = miniatura.createGraphics();
    g2d.drawImage(escalada, 0, 0, null);
    g2d.dispose();
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    javax.imageio.ImageIO.write(miniatura, "jpg", baos);
    return baos.toByteArray();
}

// Si necesitas mantener el método anterior para compatibilidad, puedes dejarlo, pero lo ideal es migrar a la versión paginada.

    /* 
     * Actualiza un usuario existente.
     * @author andres
     * 
     * @param id El ID del usuario a actualizar.
     * @param usuarioDTO El objeto DTO que contiene la nueva información del usuario.
     * @return El objeto UsuarioDto del usuario actualizado.
     */
    public CrearUsuDto actualizarUsuario(Long id, CrearUsuDto usuarioDTO) {

        // 1. Verificar que el usuario existe
        UsuarioEntidad usuarioExistente = usuarioRepositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Verificar si el nuevo correo ya existe (si se está cambiando)
        if (!usuarioExistente.getCorreoElectronico().equals(usuarioDTO.getCorreoElectronico()) &&
            usuarioRepositorio.findByCorreoElectronico(usuarioDTO.getCorreoElectronico()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        try {
            // 3. Actualizar los campos del usuario
            usuarioExistente.setNombreCompleto(usuarioDTO.getNombreCompleto());
            usuarioExistente.setMovil(usuarioDTO.getMovil());
            usuarioExistente.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
            usuarioExistente.setGoogle(usuarioDTO.isGoogle());

            // 4. Actualizar el rol si ha cambiado
            if (usuarioDTO.getRolId() != null) {
                RolEntidad nuevoRol = rolRepositorio.findById(usuarioDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("El rol especificado no existe"));
                usuarioExistente.setRol(nuevoRol);
            }

            // 5. Actualizar la contraseña solo si se proporciona una nueva
            if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().trim().isEmpty()) {
                usuarioExistente.setContrasena(usuarioDTO.getContrasena());
            }

            // 6. Actualizar la foto si se proporciona una nueva
            if (usuarioDTO.getFoto() != null) {
                usuarioExistente.setFoto(usuarioDTO.getFoto());
            }

            // 7. Guardar los cambios
            UsuarioEntidad usuarioActualizado = usuarioRepositorio.save(usuarioExistente);

            // 8. Convertir a DTO y devolver
            CrearUsuDto usuarioResponse = aDto2(usuarioActualizado);
            return usuarioResponse;

        } catch (Exception e) {
            logger.error("Error al actualizar usuario {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar usuario");
        }
    }

    /** Elimina un usuario por su ID.
     * @author andres
     * 
     * @param id El ID del usuario a eliminar.
     */
    public void eliminarUsuario(Long id) {
        try {
            if (!usuarioRepositorio.existsById(id)) {
                throw new RuntimeException("Usuario no encontrado");
            }
            usuarioRepositorio.deleteById(id);
            logger.info("Usuario eliminado correctamente. ID: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar usuario {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar usuario");
        }
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @author andres
     * 
     * @param correoElectronico El correo electrónico del usuario a buscar
     * @return UsuarioDto si existe, null si no existe
     */
    public UsuarioDto buscarPorCorreoElectronico(String correoElectronico) {
        try {
            Optional<UsuarioEntidad> usuarioOpt = usuarioRepositorio.findByCorreoElectronico(correoElectronico);
            
            if (!usuarioOpt.isPresent()) {
                return null;
            }
            UsuarioEntidad usuario = usuarioOpt.get();
            UsuarioDto usuarioDto = aDto(usuario);
            return usuarioDto;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por correo");
        }
    }
    
    /**
     * Autentica un usuario verificando sus credenciales.
     * @author andres
     * 
     * @param correoElectronico El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     * @return El objeto UsuarioDto del usuario autenticado.
     * @throws RuntimeException Si las credenciales son inválidas.
     */
    public UsuarioDto autenticarUsuario(String correoElectronico, String contrasena) {
        Optional<UsuarioEntidad> usuarioOpt = usuarioRepositorio.findByCorreoElectronico(correoElectronico);
        
        if ( !usuarioOpt.get().isCorreoConfirmado()) {
            throw new RuntimeException("Usuario no confirmado");
        }
        
        UsuarioEntidad usuario = usuarioOpt.get();

         // Verificar correo confirmado y contraseña en una sola condición
        if (!usuarioOpt.isPresent() || !contrasena.equals(usuario.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        
        return aDto(usuario);
    }

    /**
     * Confirma el correo electrónico de un usuario.
     * @author andres
     * 
     * @param email El correo electrónico del usuario a confirmar
     * @throws RuntimeException si el usuario no existe
     */
    public void confirmarCorreoUsuario(String email) {
        try {
            UsuarioEntidad usuario = usuarioRepositorio.findByCorreoElectronico(email)
                .orElseThrow(() -> {
                    logger.error("Usuario no encontrado con correo electrónico: {}", email);
                    return new RuntimeException("Usuario no encontrado");
                });
            usuario.setCorreoConfirmado(true);
            usuarioRepositorio.save(usuario);
        } catch (Exception e) {
            logger.error("Error al confirmar correo para usuario {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Error al confirmar correo");
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     * @author andres
     * 
     * @param email El correo electrónico del usuario
     * @param nuevaContrasena La nueva contraseña a establecer
     * @throws RuntimeException Si el usuario no existe o hay un error al actualizar
     */
    public void actualizarContrasenaUsuario(String email, String nuevaContrasena) {
        try {
            UsuarioEntidad usuario = usuarioRepositorio.findByCorreoElectronico(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuario.setContrasena(nuevaContrasena);
            usuarioRepositorio.save(usuario);
        } catch (Exception e) {
            logger.error("Error al actualizar contraseña para usuario {}: {}", email, e.getMessage(), e);
            // No sobreescribir el mensaje; relanza la excepción original
            throw new RuntimeException(e.getMessage(), e);
        }
    }



    /**
     * Actualiza parcialmente el perfil del usuario (nombreCompleto, movil, foto). Todos los campos son opcionales.
     * @param id El ID del usuario a actualizar
     * @param campos Mapa de campos a actualizar
     * @return UsuarioDto actualizado
     */
    public UsuarioDto actualizarPerfilParcial(Long id, Map<String, Object> campos) {
        try {
            UsuarioEntidad usuario = usuarioRepositorio.findById(id).orElseThrow(() -> {
                logger.error("Usuario no encontrado con ID: {}", id);
                return new RuntimeException("Usuario no encontrado");
            });

            // Solo actualiza campos presentes en el mapa
            if (campos.containsKey("nombreCompleto")) {
                usuario.setNombreCompleto((String) campos.get("nombreCompleto"));
            }
            if (campos.containsKey("movil")) {
                usuario.setMovil((String) campos.get("movil"));
            }
            if (campos.containsKey("foto")) {
                Object foto = campos.get("foto");
                if (foto instanceof String) {
                    // Si la foto viene como base64, decodifícala
                    usuario.setFoto(java.util.Base64.getDecoder().decode((String) foto));
                } else if (foto instanceof byte[]) {
                    usuario.setFoto((byte[]) foto);
                }
            }
            usuarioRepositorio.save(usuario);
            return aDto(usuario);
            } catch (Exception e) {
            logger.error("Error al actualizar perfil parcial para usuario {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar perfil parcial");
        }
    }

    /**
     * Convierte un UsuarioDto a una UsuarioEntidad.
     * @author andres
     * 
     * @param usuarioDTO El objeto DTO a convertir.
     * @return La entidad correspondiente.
     
    private UsuarioEntidad aEntidad(UsuarioDto usuarioDTO) {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setId(usuarioDTO.getId());
        usuarioEntidad.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuarioEntidad.setMovil(usuarioDTO.getMovil());
        usuarioEntidad.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        
        // Crear un objeto RolEntidad solo si es necesario
        if (usuarioDTO.getRolId() != null) {
            RolEntidad rol = new RolEntidad();
            rol.setId(usuarioDTO.getRolId());
            usuarioEntidad.setRol(rol);
        }
        
        usuarioEntidad.setFoto(usuarioDTO.getFoto());
        usuarioEntidad.setFechaCreacion(usuarioDTO.getFechaCreacion());
        usuarioEntidad.setCorreoConfirmado(usuarioDTO.isCorreoConfirmado());
        usuarioEntidad.setGoogle(usuarioDTO.isGoogle());
        return usuarioEntidad;
    }*/
    private UsuarioEntidad aEntidad2(CrearUsuDto crearUsuDTO) {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setNombreCompleto(crearUsuDTO.getNombreCompleto());
        usuarioEntidad.setMovil(crearUsuDTO.getMovil());
        usuarioEntidad.setCorreoElectronico(crearUsuDTO.getCorreoElectronico());
        
        // Cargar el rol completo desde la base de datos
        if (crearUsuDTO.getRolId() != null) {
            RolEntidad rol = rolRepositorio.findById(crearUsuDTO.getRolId())
                .orElseThrow(() -> new RuntimeException("El rol especificado no existe"));
            usuarioEntidad.setRol(rol);
        } else {
            throw new RuntimeException("El rol es obligatorio");
        }
        
        usuarioEntidad.setContrasena(crearUsuDTO.getContrasena());
        usuarioEntidad.setFoto(crearUsuDTO.getFoto());
        usuarioEntidad.setGoogle(crearUsuDTO.isGoogle());
        usuarioEntidad.setCorreoConfirmado(crearUsuDTO.isCorreoConfirmado());
        
        return usuarioEntidad;
    }

    /**
     * Convierte una entidad Usuario a DTO.
     * @author andres
     * 
     * @param usuarioEntidad La entidad a convertir
     * @return El DTO resultante
     */
    public UsuarioDto aDto(UsuarioEntidad usuarioEntidad) {
        if (usuarioEntidad == null) return null;
        
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuarioEntidad.getId());
        dto.setNombreCompleto(usuarioEntidad.getNombreCompleto());
        dto.setMovil(usuarioEntidad.getMovil());
        dto.setCorreoElectronico(usuarioEntidad.getCorreoElectronico());
        dto.setRolId(usuarioEntidad.getRol().getId());
        dto.setFoto(usuarioEntidad.getFoto());
        dto.setFechaCreacion(usuarioEntidad.getFechaCreacion());
        dto.setCorreoConfirmado(usuarioEntidad.isCorreoConfirmado());
        dto.setGoogle(usuarioEntidad.isGoogle());
        
        return dto;
    }

    /**
     * Convierte una entidad Usuario a DTO.
     * @author andres
     * 
     * @param usuarioEntidad La entidad a convertir
     * @return El DTO resultante
     */
    private CrearUsuDto aDto2(UsuarioEntidad usuarioEntidad) {
        CrearUsuDto usuarioDTO = new CrearUsuDto(); // Crear un nuevo DTO
        usuarioDTO.setNombreCompleto(usuarioEntidad.getNombreCompleto()); // Establecer el nombre completo
        usuarioDTO.setMovil(usuarioEntidad.getMovil()); // Establecer el número de móvil
        usuarioDTO.setCorreoElectronico(usuarioEntidad.getCorreoElectronico()); // Establecer el correo electrónico
        usuarioDTO.setRolId(usuarioEntidad.getRol().getId());
        usuarioDTO.setFoto(usuarioEntidad.getFoto()); // Establecer la foto
        usuarioDTO.setCorreoConfirmado(usuarioEntidad.isCorreoConfirmado()); // Establecer el estado de confirmación del correo
        usuarioDTO.setGoogle(usuarioEntidad.isGoogle());
        
        return usuarioDTO; // Devolver el DTO
    }
}