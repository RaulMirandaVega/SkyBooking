package com.skybooking.service.impl;

import com.skybooking.dto.AvionDTO;
import com.skybooking.exception.BusinessException;
import com.skybooking.exception.ResourceNotFoundException;
import com.skybooking.model.Avion;
import com.skybooking.model.Vuelo;
import com.skybooking.repository.AvionRepository;
import com.skybooking.repository.VueloRepository;
import com.skybooking.service.AvionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvionServiceImpl implements AvionService {

    private final AvionRepository avionRepository;
    private final VueloRepository vueloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> listarTodos() {
        return avionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AvionDTO buscarPorId(Long id) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avión no encontrado con ID: " + id));
        return convertirADTO(avion);
    }

    @Override
    @Transactional
    public AvionDTO crear(AvionDTO dto) {
        if (avionRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            throw new BusinessException("Ya existe un avión con la matrícula: " + dto.getMatricula());
        }

        Avion avion = convertirAEntidad(dto);
        return convertirADTO(avionRepository.save(avion));
    }

    @Override
    @Transactional
    public AvionDTO actualizar(Long id, AvionDTO dto) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avión no encontrado"));

        // Si cambia la matrícula, verificar que la nueva no esté ocupada
        if (!avion.getMatricula().equals(dto.getMatricula()) &&
                avionRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            throw new BusinessException("La matrícula " + dto.getMatricula() + " ya está registrada en otro avión");
        }

        avion.setMatricula(dto.getMatricula());
        avion.setModelo(dto.getModelo());
        avion.setCapacidadTurista(dto.getCapacidadTurista());
        avion.setCapacidadBusiness(dto.getCapacidadBusiness());

        return convertirADTO(avionRepository.save(avion));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!avionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avión no encontrado");
        }

        // Buscamos si este avión tiene vuelos asignados
        List<Vuelo> vuelosAsignados = vueloRepository.findByAvionId(id);

        if (!vuelosAsignados.isEmpty()) {
            throw new BusinessException("No se puede eliminar el avión porque tiene " + vuelosAsignados.size() + " vuelos asignados. Reasigne o cancele los vuelos primero.");
        }

        avionRepository.deleteById(id);
    }

    @Override
    public long countAviones() {
        return avionRepository.count();
    }

    // MAPPERS
    private AvionDTO convertirADTO(Avion e) {
        return new AvionDTO(
                e.getId(),
                e.getMatricula(),
                e.getModelo(),
                e.getCapacidadTurista(),
                e.getCapacidadBusiness()
        );
    }

    private Avion convertirAEntidad(AvionDTO d) {
        Avion a = new Avion();
        a.setMatricula(d.getMatricula());
        a.setModelo(d.getModelo());
        a.setCapacidadTurista(d.getCapacidadTurista());
        a.setCapacidadBusiness(d.getCapacidadBusiness());
        return a;
    }
}