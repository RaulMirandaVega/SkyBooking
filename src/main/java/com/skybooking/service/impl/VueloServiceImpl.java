package com.skybooking.service.impl;

import com.skybooking.dto.VueloDTO;
import com.skybooking.exception.ResourceNotFoundException;
import com.skybooking.exception.BusinessException;
import com.skybooking.model.Avion;
import com.skybooking.model.EstadoVuelo;
import com.skybooking.model.Vuelo;
import com.skybooking.repository.AvionRepository;
import com.skybooking.repository.VueloRepository;
import com.skybooking.service.VueloService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VueloServiceImpl implements VueloService {

    private final VueloRepository vueloRepository;
    private final AvionRepository avionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VueloDTO> listarTodos() {
        return vueloRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VueloDTO buscarPorId(Long id) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado a partir del ID " + id));
        return convertirADTO(vuelo);
    }

    @Override
    @Transactional
    public VueloDTO crear(VueloDTO dto) {
        if (vueloRepository.findByNumeroVuelo(dto.getNumeroVuelo()).isPresent()) {
            throw new BusinessException("Ya existe un vuelo con el número " + dto.getNumeroVuelo());
        }
        Avion avion = avionRepository.findById(dto.getAvionId())
                .orElseThrow(() -> new ResourceNotFoundException("Avion no encontrado con ID " + (dto.getAvionId())));
        Vuelo vuelo = convertirAEntidad(dto);
        vuelo.setAvion(avion);

        if (vuelo.getEstado() == null) vuelo.setEstado(EstadoVuelo.PROGRAMADO);

        return convertirADTO(vueloRepository.save(vuelo));
    }

    @Override
    @Transactional
    public VueloDTO actualizar(Long id, VueloDTO dto) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado con ID: " + id));
        vuelo.setOrigen(dto.getOrigen());
        vuelo.setDestino(dto.getDestino());
        vuelo.setFechaSalida(dto.getFechaSalida());
        vuelo.setFechaLlegada(dto.getFechaLlegada());
        vuelo.setPrecioTurista(dto.getPrecioTurista());
        vuelo.setPrecioBusiness(dto.getPrecioBusiness());

        if (dto.getEstado() != null) {
            vuelo.setEstado(EstadoVuelo.valueOf(dto.getEstado()));
        }
        if (!vuelo.getAvion().getId().equals(dto.getAvionId())) {
            Avion nuevoAvion = avionRepository.findById(dto.getAvionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avión no encontrado"));
            vuelo.setAvion(nuevoAvion);
        }

        return convertirADTO(vueloRepository.save(vuelo));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!vueloRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vuelo no encontrado");
        }
        vueloRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VueloDTO> buscarVuelosDisponibles() {
        return vueloRepository.buscarVuelosDisponibles(LocalDateTime.now()).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VueloDTO> buscarPorOrigenDestino(String origen, String destino) {
        return vueloRepository.findByOrigenAndDestino(origen, destino).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // MAPPERS
    private VueloDTO convertirADTO(Vuelo entidad) {
        VueloDTO dto = new VueloDTO();
        dto.setId(entidad.getId());
        dto.setNumeroVuelo(entidad.getNumeroVuelo());
        dto.setOrigen(entidad.getOrigen());
        dto.setDestino(entidad.getDestino());
        dto.setFechaSalida(entidad.getFechaSalida());
        dto.setFechaLlegada(entidad.getFechaLlegada());
        dto.setPrecioTurista(entidad.getPrecioTurista());
        dto.setPrecioBusiness(entidad.getPrecioBusiness());
        dto.setEstado(entidad.getEstado().toString());

        if (entidad.getAvion() != null) {
            dto.setAvionId(entidad.getAvion().getId());
            dto.setAvionModelo(entidad.getAvion().getModelo());
        }

        return dto;
    }

    private Vuelo convertirAEntidad(VueloDTO dto) {
        Vuelo vuelo = new Vuelo();
        // el id solo se setea al actualizar
        vuelo.setNumeroVuelo(dto.getNumeroVuelo());
        vuelo.setOrigen(dto.getOrigen());
        vuelo.setDestino(dto.getDestino());
        vuelo.setFechaSalida(dto.getFechaSalida());
        vuelo.setFechaLlegada(dto.getFechaLlegada());
        vuelo.setPrecioTurista(dto.getPrecioTurista());
        vuelo.setPrecioBusiness(dto.getPrecioBusiness());

        return vuelo;
    }

}