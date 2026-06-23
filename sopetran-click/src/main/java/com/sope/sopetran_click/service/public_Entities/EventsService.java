package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.EventsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.EventsResponseDTO;
import java.util.List;

public interface EventsService {
    EventsResponseDTO crear(EventsRequestDTO dto);
    EventsResponseDTO actualizar(Long id, EventsRequestDTO dto);
    EventsResponseDTO buscarPorId(Long id);
    List<EventsResponseDTO> listarTodos();
    void eliminar(Long id);
}