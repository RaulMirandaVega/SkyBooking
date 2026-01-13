package com.skybooking.service.impl;

import com.skybooking.dto.VueloDTO;
import com.skybooking.exception.ResourceNotFoundException;
import com.skybooking.exception.BusinessException;
import com.skybooking.service.VueloService;

import lombok.RequieredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VueloServiceImpl implements VueloService {

    public List<VueloDTO> listarTodos(){

    }

    @Override
    @Transactional(readOnly = true)
    public VueloDTO buscarPorId(Long id){
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado a partir del ID " + id));
        return convertirADTO(vuelo);
    }

    public VueloDTO crear(VueloDTO dto){

        return convertirADTO();
    }

    public VueloDTO actualizar(Long id, VueloDTO dto){

    }

    public void eliminar(Long id){

    }

    public List<VueloDTO> buscarVuelosDisponibles(){

    }

    public List<VueloDTO> buscarPorOrigenDestino(String origen, String destino){

    }

    // MAPPERS

    private VueloDTO convertirADTO(Vuelo entidad){
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

        if (entidad.getAvion() != null){
            dto.setAvionId(entidad.getAvion().getId());
            dto.setAvionModelo(entidad.getAvion().getModelo());
        }
        return dto;
    }

    private Vuelo convertirAEntidad(VueloDTO dto){
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