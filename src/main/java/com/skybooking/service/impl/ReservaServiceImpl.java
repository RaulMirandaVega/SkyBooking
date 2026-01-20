package com.skybooking.service.impl;

import com.skybooking.dto.ReservaDTO;
import com.skybooking.exception.BusinessException;
import com.skybooking.exception.ResourceNotFoundException;
import com.skybooking.model.*;
import com.skybooking.repository.PasajeroRepository;
import com.skybooking.repository.ReservaRepository;
import com.skybooking.repository.VueloRepository;
import com.skybooking.service.ReservaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final VueloRepository vueloRepository;
    private final PasajeroRepository pasajeroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarTodas() {
        return reservaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        return convertirADTO(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO buscarPorCodigo(String codigo) {
        Reserva reserva = reservaRepository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con código: " + codigo));
        return convertirADTO(reserva);
    }

    @Override
    @Transactional
    public ReservaDTO crear(ReservaDTO dto) {
        Vuelo vuelo = vueloRepository.findById(dto.getVueloId())
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado"));

        if (vuelo.getEstado() != EstadoVuelo.PROGRAMADO) {
            throw new BusinessException("El vuelo no está disponible para reservas (Estado: " + vuelo.getEstado() + ")");
        }

        Pasajero pasajero = pasajeroRepository.findById(dto.getPasajeroId())
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado"));

        ClaseAsiento claseSeleccionada;
        try {
            claseSeleccionada = ClaseAsiento.valueOf(dto.getClase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BusinessException("Clase inválida. Valores permitidos: TURISTA, BUSINESS");
        }

        // Obtenemos todas las reservas del vuelo para filtrar cuántas hay de esta clase
        long ocupadasEnClase = reservaRepository.findByVueloId(vuelo.getId()).stream()
                .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA)
                .filter(r -> r.getClase() == claseSeleccionada)
                .count();

        // La capacidad total es Businness. Si = getCapacidadBusiness. No = getCapacidadTuristsa. Igual que un if-else
        // Comprobamos la clase y obtenemos los datos directamente del modelo.
        int capacidadTotal = (claseSeleccionada == ClaseAsiento.BUSINESS)
                ? vuelo.getAvion().getCapacidadBusiness()
                : vuelo.getAvion().getCapacidadTurista();

        if (ocupadasEnClase >= capacidadTotal) {
            throw new BusinessException("No hay asientos disponibles en clase " + claseSeleccionada);
        }

        // Construir la Reserva
        Reserva reserva = new Reserva();
        reserva.setVuelo(vuelo);
        reserva.setPasajero(pasajero);
        reserva.setClase(claseSeleccionada);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado(EstadoReserva.CONFIRMADA);

        // Generar código único
        reserva.setCodigoReserva("SKY-" + System.currentTimeMillis());

        // Calcular precio automático
        reserva.setPrecioTotal(
                (claseSeleccionada == ClaseAsiento.BUSINESS) ? vuelo.getPrecioBusiness() : vuelo.getPrecioTurista()
        );

        // Asiento opcional
        if(dto.getAsiento() != null && !dto.getAsiento().isBlank()) {
            reserva.setAsiento(dto.getAsiento());
        }

        return convertirADTO(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new BusinessException("La reserva ya se encuentra cancelada");
        }

        // Opcional: Validar que no se cancele un vuelo que ya salió
        if (reserva.getVuelo().getFechaSalida().isBefore(LocalDateTime.now())) {
            throw new BusinessException("No se puede cancelar una reserva de un vuelo pasado");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarPorPasajero(Long pasajeroId) {
        return reservaRepository.findByPasajeroId(pasajeroId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarPorVuelo(Long vueloId) {
        return reservaRepository.findByVueloId(vueloId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // MAPPERS
    private ReservaDTO convertirADTO(Reserva e) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(e.getId());
        dto.setCodigoReserva(e.getCodigoReserva());
        dto.setFechaReserva(e.getFechaReserva());
        dto.setPrecioTotal(e.getPrecioTotal());
        dto.setEstado(e.getEstado().toString());
        dto.setClase(e.getClase().toString());
        dto.setAsiento(e.getAsiento());

        if (e.getVuelo() != null) {
            dto.setVueloId(e.getVuelo().getId());
            dto.setVueloNumero(e.getVuelo().getNumeroVuelo());
        }
        if (e.getPasajero() != null) {
            dto.setPasajeroId(e.getPasajero().getId());
            dto.setPasajeroNombre(e.getPasajero().getNombre() + " " + e.getPasajero().getApellidos());
        }
        return dto;
    }
}


