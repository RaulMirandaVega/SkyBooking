package com.skybooking.controller.rest;


import com.skybooking.dto.JwtResponse;
import com.skybooking.dto.LoginRequest;
import com.skybooking.dto.RegistroRequest;
import com.skybooking.model.Rol;
import com.skybooking.model.Usuario;
import com.skybooking.repository.RolRepository;
import com.skybooking.repository.UsuarioRepository;
import com.skybooking.security.UserDetailsImpl;
import com.skybooking.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skybooking.exception.ErrorResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    // ===================== LOGIN =====================

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails =
                    (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            JwtResponse response = new JwtResponse(
                    jwt,
                    "Bearer",
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles
            );

            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Credenciales inválidas",
                    java.time.LocalDateTime.now()
            );
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }


    // ===================== REGISTRO =====================
    @PostMapping("/registro")
    public ResponseEntity<?> register(@Valid @RequestBody RegistroRequest registroRequest) {

        if (usuarioRepository.existsByUsername(registroRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El nombre de usuario ya existe");
        }

        if (usuarioRepository.existsByEmail(registroRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registroRequest.getUsername());
        usuario.setEmail(registroRequest.getEmail());
        usuario.setPassword(encoder.encode(registroRequest.getPassword()));
        usuario.setNombre(registroRequest.getNombre());
        usuario.setApellidos(registroRequest.getApellidos());
        usuario.setEnabled(true);

        // Rol por defecto: ROLE_USER
        Rol rolUser = rolRepository.findByNombre(Rol.NombreRol.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente");
    }
}
