package com.skybooking.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHashPrinter implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String raw = "123456";
        String hash = passwordEncoder.encode(raw);
        System.out.println("[PasswordHashPrinter] BCrypt hash for '123456': " + hash);
    }
}
