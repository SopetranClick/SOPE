package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.HistoryEntryRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.HistoryEntryResponseDTO;

import java.util.List;

public interface HistoryEntryService {
    HistoryEntryResponseDTO crear(HistoryEntryRequestDTO dto);
    HistoryEntryResponseDTO actualizar(Long id, HistoryEntryRequestDTO dto);
    HistoryEntryResponseDTO buscarPorId(Long id);
    List<HistoryEntryResponseDTO> listarTodos();
    void eliminar(Long id);
}
