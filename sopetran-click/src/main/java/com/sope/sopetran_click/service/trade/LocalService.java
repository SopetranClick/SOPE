package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.LocalRequestDTO;
import com.sope.sopetran_click.dto.trade.LocalResponseDTO;

import java.util.List;

public interface LocalService {

    LocalResponseDTO crearLocal(LocalRequestDTO dto);
    LocalResponseDTO actualizarLocal(Long id, LocalRequestDTO dto);
    LocalResponseDTO buscarPorId(Long id);
    List<LocalResponseDTO> listarTodos();
    void eliminarLocal(Long id);
}
