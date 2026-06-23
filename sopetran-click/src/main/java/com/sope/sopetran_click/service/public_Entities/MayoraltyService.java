package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.MayoraltyRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.MayoraltyResponseDTO;
import java.util.List;

public interface MayoraltyService {
    MayoraltyResponseDTO crear(MayoraltyRequestDTO dto);
    MayoraltyResponseDTO actualizar(Long id, MayoraltyRequestDTO dto);
    MayoraltyResponseDTO buscarPorId(Long id);
    List<MayoraltyResponseDTO> listarTodos();
    void eliminar(Long id);
}