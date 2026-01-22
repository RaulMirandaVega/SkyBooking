package com.skybooking.controller.web;

import com.skybooking.dto.AvionDTO;
import com.skybooking.model.Avion;
import com.skybooking.service.AvionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/web/aviones")
public class AvionWebController {

    private final AvionService avionService;

    public AvionWebController(AvionService avionService) {
        this.avionService = avionService;
    }

    @GetMapping
    public String listar(Model model) {
        List<AvionDTO> lista = avionService.listarTodos();
        System.out.println("Aviones: " + lista); // 🔹 debug
        model.addAttribute("aviones", lista);
        return "aviones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("avion", new AvionDTO());
        return "aviones/formulario";
    }


    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("avion") AvionDTO avionDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "aviones/formulario";
        }

        avionService.crear(avionDTO); // ✅ Usar DTO y servicio
        redirectAttributes.addFlashAttribute("success", "Avión guardado correctamente");
        return "redirect:/web/aviones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("avion", avionService.buscarPorId(id)); // devuelve AvionDTO
        return "aviones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        avionService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Avión eliminado");
        return "redirect:/web/aviones";
    }
}
