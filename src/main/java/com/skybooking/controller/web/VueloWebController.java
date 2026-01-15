package com.skybooking.controller.web;


import com.skybooking.model.Vuelo;
import com.skybooking.service.VueloService;
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

    public VueloWebController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vuelos", vueloService.listarTodos());
        return "vuelos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("vuelo", new Vuelo());
        return "vuelos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("vuelo") Vuelo vuelo,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "vuelos/formulario";
        }

        vueloService.guardar(vuelo);
        redirectAttributes.addFlashAttribute("success", "Vuelo guardado correctamente");
        return "redirect:/web/vuelos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("vuelo", vueloService.buscarPorId(id));
        return "vuelos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        vueloService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Vuelo eliminado correctamente");
        return "redirect:/web/vuelos";
    }
}

