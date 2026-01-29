package com.skybooking.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    // ===================== LOGIN =====================
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "Has cerrado sesión correctamente");
        }

        return "login"; // nombre del template login.html
    }

    // ===================== ACCESO DENEGADO =====================
    @GetMapping("/acceso-denegado")
    public String accesoDenegado(Model model) {
        model.addAttribute("mensaje", "No tienes permisos para acceder a esta página");
        return "acceso-denegado"; // template acceso-denegado.html
    }

    // ===================== DASHBOARD =====================
    @GetMapping("/web/dashboard")
    public String dashboard() {
        return "dashboard"; // template dashboard.html
    }
}
