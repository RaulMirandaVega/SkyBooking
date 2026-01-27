package com.skybooking.repository;

import com.skybooking.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Login (UserDetailsServiceImpl)
    Optional<Usuario> findByUsername(String username);

    // Alternativa de login (recuperación)
    Optional<Usuario> findByEmail(String email);

    // Registro de nuevos usuarios
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}