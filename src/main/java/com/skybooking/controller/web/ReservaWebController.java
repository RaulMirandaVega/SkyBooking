package com.skybooking.controller.web;


import com.skybooking.model.Reserva;
import com.skybooking.service.PasajeroService;
import com.skybooking.service.ReservaService;
import com.skybooking.service.VueloService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/reservas")
public class ReservaWebController {

    private final ReservaService reservaService;
    private final PasajeroService pasajeroService;
    private final VueloService vueloService;

    public ReservaWebController(ReservaService reservaService,
                                PasajeroService pasajeroService,
                                VueloService vueloService) {
        this.reservaService = reservaService;
        this.pasajeroService = pasajeroService;
        this.vueloService = vueloService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "reservas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("pasajeros", pasajeroService.listarTodos());
        model.addAttribute("vuelos", vueloService.listarTodos());
        return "reservas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("reserva") Reserva reserva,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("pasajeros", pasajeroService.listarTodos());
            model.addAttribute("vuelos", vueloService.listarTodos());
            return "reservas/formulario";
        }

        reservaService.guardar(reserva);
        redirectAttributes.addFlashAttribute("success", "Reserva guardada correctamente");
        return "redirect:/web/reservas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.buscarPorId(id));
        model.addAttribute("pasajeros", pasajeroService.listarTodos());
        model.addAttribute("vuelos", vueloService.listarTodos());
        return "reservas/formulario";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservaService.cancelar(id);
        redirectAttributes.addFlashAttribute("success", "Reserva cancelada");
        return "redirect:/web/reservas";
    }
}

