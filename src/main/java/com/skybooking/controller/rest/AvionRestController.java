package com.skybooking.controller.rest;

import com.skybooking.dto.AvionDTO;
import com.skybooking.service.AvionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviones")
@RequiredArgsConstructor
public class AvionRestController {

    private final AvionService avionService;

    @GetMapping
    public ResponseEntity<List<AvionDTO>> listarTodos() {
        return ResponseEntity.ok(avionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvionDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AvionDTO> crear(@Valid @RequestBody AvionDTO dto) {
        AvionDTO nuevoAvion = avionService.crear(dto);
        return new ResponseEntity<>(nuevoAvion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AvionDTO dto) {
        return ResponseEntity.ok(avionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        avionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}