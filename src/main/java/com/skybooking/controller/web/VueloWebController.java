package com.skybooking.controller.web;


import com.skybooking.dto.VueloDTO;
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
        model.addAttribute("vuelo", new VueloDTO());
        return "vuelos/formulario";
    }


    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("vuelo") VueloDTO vueloDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "vuelos/formulario";
        }

        if (vueloDTO.getId() != null) {
            // Es un vuelo existente: cargar la entidad y actualizar campos
            VueloDTO vueloExistente = vueloService.buscarPorId(vueloDTO.getId());

            vueloExistente.setNumeroVuelo(vueloDTO.getNumeroVuelo());
            vueloExistente.setOrigen(vueloDTO.getOrigen());
            vueloExistente.setDestino(vueloDTO.getDestino());
            vueloExistente.setFechaSalida(vueloDTO.getFechaSalida());
            vueloExistente.setFechaLlegada(vueloDTO.getFechaLlegada());
            vueloExistente.setPrecioTurista(vueloDTO.getPrecioTurista());
            vueloExistente.setPrecioBusiness(vueloDTO.getPrecioBusiness());
            vueloExistente.setAvionId(vueloDTO.getAvionId());
            vueloExistente.setAvionModelo(vueloDTO.getAvionModelo());
            vueloExistente.setEstado(vueloDTO.getEstado());
            vueloService.crear(vueloExistente);

            redirectAttributes.addFlashAttribute("success", "Vuelo actualizado correctamente");
        } else {
            // Nuevo vuelo
            vueloService.crear(vueloDTO);
            redirectAttributes.addFlashAttribute("success", "Vuelo creado correctamente");
        }

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

