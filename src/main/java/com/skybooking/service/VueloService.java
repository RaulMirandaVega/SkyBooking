package com.skybooking.service;

import com.skybooking.dto.VueloDTO;
import java.util.List;

public interface VueloService {

    List<VueloDTO> listarTodos();
    VueloDTO buscarPorId(Long id);
    VueloDTO crear(VueloDTO dto);
    VueloDTO actualizar(Long id, VueloDTO dto);
    void eliminar(Long id);

    List<VueloDTO> buscarVuelosDisponibles();
    List<VueloDTO> buscarPorOrigenDestino(String origen, String destino);
}
