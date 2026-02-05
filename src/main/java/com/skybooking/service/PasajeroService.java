package com.skybooking.service;

import com.skybooking.dto.PasajeroDTO;
import java.util.List;

public interface PasajeroService {
    List<PasajeroDTO> listarTodos();
    PasajeroDTO buscarPorId(Long id);
    PasajeroDTO buscarPorDni(String dni);
    List<PasajeroDTO> buscarPorNombre(String termino);
    PasajeroDTO crear(PasajeroDTO dto);
    PasajeroDTO actualizar(Long id, PasajeroDTO dto);
    void eliminar(Long id);

    long countPasajeros();
}