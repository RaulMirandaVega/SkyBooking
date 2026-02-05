package com.skybooking.service.impl;

import com.skybooking.dto.PasajeroDTO;
import com.skybooking.exception.BusinessException;
import com.skybooking.exception.ResourceNotFoundException;
import com.skybooking.model.Pasajero;
import com.skybooking.model.Reserva;
import com.skybooking.repository.PasajeroRepository;
import com.skybooking.repository.ReservaRepository;
import com.skybooking.service.PasajeroService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasajeroServiceImpl implements PasajeroService {

    private final PasajeroRepository pasajeroRepository;
    private final ReservaRepository reservaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PasajeroDTO> listarTodos() {
        return pasajeroRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PasajeroDTO buscarPorId(Long id) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado con ID: " + id));
        return convertirADTO(pasajero);
    }

    @Override
    @Transactional(readOnly = true)
    public PasajeroDTO buscarPorDni(String dni) {
        Pasajero pasajero = pasajeroRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado con DNI: " + dni));
        return convertirADTO(pasajero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasajeroDTO> buscarPorNombre(String termino) {
        // Usamos la @Query del repositorio
        return pasajeroRepository.buscarPorNombre(termino).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PasajeroDTO crear(PasajeroDTO dto) {
        if (pasajeroRepository.findByDni(dto.getDni()).isPresent()) {
            throw new BusinessException("Ya existe un pasajero con el DNI: " + dto.getDni());
        }

        if (pasajeroRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("Ya existe un pasajero con el email: " + dto.getEmail());
        }

        Pasajero pasajero = convertirAEntidad(dto);
        return convertirADTO(pasajeroRepository.save(pasajero));
    }

    @Override
    @Transactional
    public PasajeroDTO actualizar(Long id, PasajeroDTO dto) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado"));

        // Si el DNI nuevo es distinto al que ya tenía Y ese DNI nuevo ya existe en BD devuelve un error
        if (!pasajero.getDni().equals(dto.getDni()) &&
                pasajeroRepository.findByDni(dto.getDni()).isPresent()) {
            throw new BusinessException("El DNI " + dto.getDni() + " ya está en uso por otro pasajero");
        }

        if (!pasajero.getEmail().equals(dto.getEmail()) &&
                pasajeroRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("El email " + dto.getEmail() + " ya está en uso");
        }

        // Actualizamos los campos
        pasajero.setNombre(dto.getNombre());
        pasajero.setApellidos(dto.getApellidos());
        pasajero.setDni(dto.getDni());
        pasajero.setEmail(dto.getEmail());
        pasajero.setTelefono(dto.getTelefono());
        pasajero.setFechaNacimiento(dto.getFechaNacimiento());

        return convertirADTO(pasajeroRepository.save(pasajero));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!pasajeroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pasajero no encontrado");
        }
        List<Reserva> reservasPasajero = reservaRepository.findByPasajeroId(id);
        if (!reservasPasajero.isEmpty()) {
            throw new BusinessException("No se puede eliminar al pasajero por tener reservas asociadas. ");
        }

        pasajeroRepository.deleteById(id);
    }

    @Override
    public long countPasajeros() {
        return pasajeroRepository.count();
    }

    // MAPPERS
    private PasajeroDTO convertirADTO(Pasajero e) {
        return new PasajeroDTO(
                e.getId(),
                e.getNombre(),
                e.getApellidos(),
                e.getDni(),
                e.getEmail(),
                e.getTelefono(),
                e.getFechaNacimiento()
        );
    }

    private Pasajero convertirAEntidad(PasajeroDTO d) {
        Pasajero p = new Pasajero();
        p.setNombre(d.getNombre());
        p.setApellidos(d.getApellidos());
        p.setDni(d.getDni());
        p.setEmail(d.getEmail());
        p.setTelefono(d.getTelefono());
        p.setFechaNacimiento(d.getFechaNacimiento());
        return p;
    }
}