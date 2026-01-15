package com.skybooking.controller.rest;

import com.skybooking.dto.PasajeroDTO;
import com.skybooking.service.PasajeroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasajeros")
@RequiredArgsConstructor
public class PasajeroRestController {

    private final PasajeroService pasajeroService;

    // GET - Listar todos
    @GetMapping
    public ResponseEntity<List<PasajeroDTO>> listarTodos() {
        return ResponseEntity.ok(pasajeroService.listarTodos());
    }

    // GET - Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<PasajeroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pasajeroService.buscarPorId(id));
    }

    // GET - Buscar por DNI
    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<PasajeroDTO> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(pasajeroService.buscarPorDni(dni));
    }

    // GET - Búsqueda parcial
    @GetMapping("/buscar")
    public ResponseEntity<List<PasajeroDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(pasajeroService.buscarPorNombre(nombre));
    }

    // POST - Crear
    @PostMapping
    public ResponseEntity<PasajeroDTO> crear(@Valid @RequestBody PasajeroDTO dto) {
        PasajeroDTO nuevoPasajero = pasajeroService.crear(dto);
        return new ResponseEntity<>(nuevoPasajero, HttpStatus.CREATED);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PasajeroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PasajeroDTO dto) {
        return ResponseEntity.ok(pasajeroService.actualizar(id, dto));
    }

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pasajeroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}