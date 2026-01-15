package com.skybooking.controller.rest;

import com.skybooking.dto.VueloDTO;
import com.skybooking.service.VueloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
public class VueloRestController {

    private final VueloService vueloService;

    // GET - Listar todos
    @GetMapping
    public ResponseEntity<List<VueloDTO>> listarTodos() {
        return ResponseEntity.ok(vueloService.listarTodos());
    }

    // GET - Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<VueloDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vueloService.buscarPorId(id));
    }

    // GET - /api/vuelos/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<VueloDTO>> listarDisponibles() {
        return ResponseEntity.ok(vueloService.buscarVuelosDisponibles());
    }

    // GET - /api/vuelos/buscar?origen=Madrid&destino=Paris
    @GetMapping("/buscar")
    public ResponseEntity<List<VueloDTO>> buscarPorRuta(
            @RequestParam String origen,
            @RequestParam String destino) {
        return ResponseEntity.ok(vueloService.buscarPorOrigenDestino(origen, destino));
    }

    // POST - Crear nuevo
    @PostMapping
    public ResponseEntity<VueloDTO> crear(@Valid @RequestBody VueloDTO dto) {
        VueloDTO nuevoVuelo = vueloService.crear(dto);
        return new ResponseEntity<>(nuevoVuelo, HttpStatus.CREATED); // Devuelve 201
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<VueloDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VueloDTO dto) {
        return ResponseEntity.ok(vueloService.actualizar(id, dto));
    }

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}