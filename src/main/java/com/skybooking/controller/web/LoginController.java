package com.skybooking.controller.web;

import com.skybooking.model.Avion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.skybooking.service.VueloService;
import com.skybooking.service.ReservaService;
import com.skybooking.service.PasajeroService;
import com.skybooking.service.AvionService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final VueloService vueloService;
    private final ReservaService reservaService;
    private final PasajeroService pasajeroService;
    private final AvionService avionService;

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
    public String dashboard(Model model) {

        // Totales
        long totalVuelos = vueloService.countVuelos();
        long totalReservas = reservaService.countReservas();
        long totalPasajeros = pasajeroService.countPasajeros();
        long totalAviones = avionService.countAviones();

        model.addAttribute("totalVuelos", totalVuelos);
        model.addAttribute("totalReservas", totalReservas);
        model.addAttribute("totalPasajeros", totalPasajeros);
        model.addAttribute("totalAviones", totalAviones);

        return "dashboard";
    }


}
