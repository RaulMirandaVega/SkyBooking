package com.skybooking.controller.web;


import com.skybooking.dto.ReservaDTO;
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
        model.addAttribute("reserva", new ReservaDTO());
        model.addAttribute("pasajeros", pasajeroService.listarTodos());
        model.addAttribute("vuelos", vueloService.listarTodos());
        return "reservas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("reserva") ReservaDTO reservaDTO,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // Si hay errores, recargar los datos de vuelos y pasajeros
        if (result.hasErrors()) {
            model.addAttribute("pasajeros", pasajeroService.listarTodos());
            model.addAttribute("vuelos", vueloService.listarTodos());
            return "reservas/formulario";
        }

        // Obtener el vuelo seleccionado para calcular precio
        var vueloSeleccionado = vueloService.buscarPorId(reservaDTO.getVueloId());

        if (vueloSeleccionado != null) {
            if ("TURISTA".equals(reservaDTO.getClase())) {
                reservaDTO.setPrecioTotal(vueloSeleccionado.getPrecioTurista());
            } else if ("BUSINESS".equals(reservaDTO.getClase())) {
                reservaDTO.setPrecioTotal(vueloSeleccionado.getPrecioBusiness());
            }
        }

        // Si no tiene estado, asignar por defecto "PENDIENTE"
        if (reservaDTO.getEstado() == null) {
            reservaDTO.setEstado("PENDIENTE");
        }

        // Guardar la reserva (funciona tanto para creación como para edición)
        reservaService.crear(reservaDTO);

        redirectAttributes.addFlashAttribute("success",
                reservaDTO.getId() != null ? "Reserva actualizada correctamente" : "Reserva creada correctamente");

        return "redirect:/web/reservas";
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        var reserva = reservaService.buscarPorId(id);
        if (reserva != null) {
            // Cambiar entre PENDIENTE → CONFIRMADA → CANCELADA → PENDIENTE
            switch (reserva.getEstado()) {
                case "PENDIENTE" -> reserva.setEstado("CONFIRMADA");
                case "CONFIRMADA" -> reserva.setEstado("CANCELADA");
                default -> reserva.setEstado("PENDIENTE");
            }
            reservaService.crear(reserva); // reutiliza crear para guardar cambios
            redirectAttributes.addFlashAttribute("success", "Estado actualizado correctamente");
        }
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

