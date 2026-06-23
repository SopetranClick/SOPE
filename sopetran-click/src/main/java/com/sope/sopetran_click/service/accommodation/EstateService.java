package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.EstateRequestDTO;
import com.sope.sopetran_click.dto.accommodation.EstateResponseDTO;
import java.util.List;
public interface EstateService {

    EstateResponseDTO crearFinca(EstateRequestDTO dto);
    EstateResponseDTO actualizarFinca(Long id, EstateRequestDTO dto);
    EstateResponseDTO buscarPorId(Long id);
    List<EstateResponseDTO> listarTodos();
    void eliminarFinca(Long id);
}
