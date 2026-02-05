package com.skybooking.controller.web;

import com.skybooking.dto.PasajeroDTO;
import com.skybooking.model.Pasajero;
import com.skybooking.service.PasajeroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/pasajeros")
public class PasajeroWebController {

    private final PasajeroService pasajeroService;

    public PasajeroWebController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pasajeros", pasajeroService.listarTodos());
        return "pasajeros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pasajero", new PasajeroDTO());
        return "pasajeros/formulario";
    }


    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("pasajero") PasajeroDTO pasajeroDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "pasajeros/formulario";
        }

        if (pasajeroDTO.getId() == null) {
            // CREAR nuevo pasajero
            pasajeroService.crear(pasajeroDTO);
            redirectAttributes.addFlashAttribute("success", "Pasajero creado correctamente");
        } else {
            // ACTUALIZAR pasajero existente
            pasajeroService.actualizar(pasajeroDTO.getId(), pasajeroDTO);
            redirectAttributes.addFlashAttribute("success", "Pasajero actualizado correctamente");
        }

        return "redirect:/web/pasajeros";
    }



    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pasajero", pasajeroService.buscarPorId(id));
        return "pasajeros/formulario";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pasajeroService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Pasajero eliminado");
        return "redirect:/web/pasajeros";
    }
}

