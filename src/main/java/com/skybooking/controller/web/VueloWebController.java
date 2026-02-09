package com.skybooking.controller.web;

import com.skybooking.dto.VueloDTO;
import com.skybooking.model.EstadoVuelo;
import com.skybooking.service.VueloService;
import com.skybooking.service.AvionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/vuelos")
public class VueloWebController {

    private final VueloService vueloService;
    private final AvionService avionService;

    public VueloWebController(VueloService vueloService, AvionService avionService) {
        this.vueloService = vueloService;
        this.avionService = avionService;
    }

    // LISTA (siempre desde BD)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vuelos", vueloService.listarTodos());
        return "vuelos/lista";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("vuelo", new VueloDTO());
        model.addAttribute("aviones", avionService.listarTodos());
        model.addAttribute("estados", EstadoVuelo.values());
        return "vuelos/formulario";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("vuelo", vueloService.buscarPorId(id));
        model.addAttribute("aviones", avionService.listarTodos());
        model.addAttribute("estados", EstadoVuelo.values());
        return "vuelos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("vuelo") VueloDTO vueloDTO,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("aviones", avionService.listarTodos());
            model.addAttribute("estados", EstadoVuelo.values());
            return "vuelos/formulario";
        }

        if (vueloDTO.getId() != null) {
            // UPDATE
            vueloService.actualizar(vueloDTO.getId(), vueloDTO);
            redirectAttributes.addFlashAttribute("success", "Vuelo actualizado correctamente");
        } else {
            // CREATE
            vueloService.crear(vueloDTO);
            redirectAttributes.addFlashAttribute("success", "Vuelo creado correctamente");
        }

        return "redirect:/web/vuelos";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {

        vueloService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Vuelo eliminado correctamente");
        return "redirect:/web/vuelos";
    }
}

