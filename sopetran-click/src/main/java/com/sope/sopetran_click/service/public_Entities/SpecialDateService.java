package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.SpecialDateRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.SpecialDateResponseDTO;

import java.util.List;

public interface SpecialDateService {
    SpecialDateResponseDTO crear(SpecialDateRequestDTO dto);
    SpecialDateResponseDTO actualizar(Long id, SpecialDateRequestDTO dto);
    SpecialDateResponseDTO buscarPorId(Long id);
    List<SpecialDateResponseDTO> listarTodos();
    void eliminar(Long id);
}
