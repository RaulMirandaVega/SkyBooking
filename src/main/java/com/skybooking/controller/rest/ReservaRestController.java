package com.skybooking.controller.rest;

import com.skybooking.dto.ReservaDTO;
import com.skybooking.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaRestController {

    private final ReservaService reservaService;

    // GET - Listar todas
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    // GET - Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    // GET - Buscar por localizador
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ReservaDTO> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(reservaService.buscarPorCodigo(codigo));
    }

    // GET - Historial de un pasajero
    @GetMapping("/pasajero/{pasajeroId}")
    public ResponseEntity<List<ReservaDTO>> listarPorPasajero(@PathVariable Long pasajeroId) {
        return ResponseEntity.ok(reservaService.listarPorPasajero(pasajeroId));
    }

    // POST - Crear nueva reserva
    @PostMapping
    public ResponseEntity<ReservaDTO> crear(@Valid @RequestBody ReservaDTO dto) {
        ReservaDTO nuevaReserva = reservaService.crear(dto);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    // DELETE - Cancelar reserva
    // DELETE para la acción de cancelar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}