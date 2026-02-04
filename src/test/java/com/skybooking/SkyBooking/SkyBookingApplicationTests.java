package com.skybooking.SkyBooking;

import com.skybooking.model.Usuario;
import com.skybooking.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SkyBookingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	void verifyAdminPasswordMatch() {
		Usuario usuario = usuarioRepository.findByUsername("admin")
				.orElseThrow(() -> new AssertionError("Usuario 'admin' no encontrado"));

		String stored = usuario.getPassword();
		System.out.println("Stored password for admin: [" + stored + "] length=" + (stored == null ? 0 : stored.length()));
		boolean matches = passwordEncoder.matches("123456", stored);
		System.out.println("BCrypt matches('123456', stored) = " + matches);
		System.out.println("startsWith $2: " + (stored != null && stored.startsWith("$2")));

		assertTrue(matches, "Password mismatch for 'admin'. Check DB stored value and trimming.");
	}

}
