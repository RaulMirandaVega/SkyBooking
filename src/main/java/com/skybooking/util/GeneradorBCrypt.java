package com.skybooking.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("123456");
        String hash = encoder.encode(password);
        System.out.println("Hash: " + hash);
    }
}
