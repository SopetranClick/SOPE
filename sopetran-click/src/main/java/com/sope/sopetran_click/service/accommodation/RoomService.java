package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import com.sope.sopetran_click.dto.accommodation.RoomRequestDTO;
import com.sope.sopetran_click.dto.accommodation.RoomResponseDTO;

import java.util.List;

public interface RoomService {

    RoomResponseDTO buscarPorId(Long id);
    HotelResponseDTO buscarPorIdhotel(Long idhotel);
    List<RoomResponseDTO> listarTodos();
    void eliminarHabitacion(Long id);
    RoomResponseDTO actualizarHabitacion(Long id, RoomRequestDTO habitacion);
    RoomResponseDTO crearHabitacion(RoomRequestDTO habitacion);
}
