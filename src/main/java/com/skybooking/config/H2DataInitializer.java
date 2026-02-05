package com.skybooking.config;

import com.skybooking.model.Rol;
import com.skybooking.model.Usuario;
import com.skybooking.repository.RolRepository;
import com.skybooking.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Profile("h2")
@RequiredArgsConstructor
public class H2DataInitializer {
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Roles
        for (Rol.NombreRol nr : Rol.NombreRol.values()) {
            Optional<Rol> r = rolRepository.findByNombre(nr);
            if (r.isEmpty()) {
                Rol nuevo = new Rol();
                nuevo.setNombre(nr);
                rolRepository.save(nuevo);
            }
        }

        // Usuarios de prueba
        createUserIfNotExists("admin", "admin@skybooking.com", "Administrador", "Sistema", "123456", new Rol.NombreRol[]{Rol.NombreRol.ROLE_USER, Rol.NombreRol.ROLE_ADMIN, Rol.NombreRol.ROLE_EMPLEADO});
        createUserIfNotExists("empleado", "empleado@skybooking.com", "Empleado", "Prueba", "123456", new Rol.NombreRol[]{Rol.NombreRol.ROLE_USER, Rol.NombreRol.ROLE_EMPLEADO});
        createUserIfNotExists("usuario", "usuario@skybooking.com", "Usuario", "Normal", "123456", new Rol.NombreRol[]{Rol.NombreRol.ROLE_USER});
    }

    private void createUserIfNotExists(String username, String email, String nombre, String apellido, String rawPassword, Rol.NombreRol[] rolesToAssign) {
        if (usuarioRepository.existsByUsername(username)) return;

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setEmail(email);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setEnabled(true);

        Set<Rol> roles = new HashSet<>();
        for (Rol.NombreRol nr : rolesToAssign) {
            Rol rol = rolRepository.findByNombre(nr).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nr));
            roles.add(rol);
        }
        u.setRoles(roles);

        usuarioRepository.save(u);
    }
}
