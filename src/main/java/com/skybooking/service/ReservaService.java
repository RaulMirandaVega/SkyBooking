package com.skybooking.service;

import com.skybooking.dto.ReservaDTO;
import java.util.List;

public interface ReservaService{
    List<ReservaDTO> listarTodas();
    ReservaDTO buscarPorId(Long id);
    ReservaDTO buscarPorCodigo(String codigo);
    ReservaDTO crear(ReservaDTO dto);
    void cancelar(Long id);

    List<ReservaDTO> listarPorPasajero(Long pasajeroId);
    List<ReservaDTO> listarPorVuelo(Long vueloId);

    long countReservas();
}

