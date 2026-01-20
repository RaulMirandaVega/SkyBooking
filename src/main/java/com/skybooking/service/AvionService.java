package com.skybooking.service;

import com.skybooking.dto.AvionDTO;
import java.util.List;

public interface AvionService {
    List<AvionDTO> listarTodos();
    AvionDTO buscarPorId(Long id);
    AvionDTO crear(AvionDTO dto);
    AvionDTO actualizar(Long id, AvionDTO dto);
    void eliminar(Long id);
}