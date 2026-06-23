package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.HotelRequestDTO;
import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import java.util.List;

public interface HotelsService {
    HotelResponseDTO crearHotel(HotelRequestDTO dto);
    HotelResponseDTO actualizarHotel(Long id, HotelRequestDTO dto);
    HotelResponseDTO buscarPorId(Long id);
    List<HotelResponseDTO> listarTodos();
    void eliminarHotel(Long id);
}