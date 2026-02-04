package com.skybooking.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plain = "123456";
        String hash = encoder.encode(plain);
        System.out.println("Generated hash: " + hash);
        System.out.println("Check matches: " + encoder.matches(plain, hash));
        System.out.println("-- SQL to update users:");
        System.out.println("UPDATE usuarios SET password = '" + hash + "' WHERE username = 'admin';");
        System.out.println("UPDATE usuarios SET password = '" + hash + "' WHERE username = 'empleado';");
        System.out.println("UPDATE usuarios SET password = '" + hash + "' WHERE username = 'usuario';");
    }
}
