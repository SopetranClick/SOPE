package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.DishRequestDTO;
import com.sope.sopetran_click.dto.trade.DishResponseDTO;

import java.util.List;

public interface DishService {

    DishResponseDTO crearPlato(DishRequestDTO dto);
    DishResponseDTO actualizarPlato(Long id, DishRequestDTO dto);
    DishResponseDTO buscarPorId(Long id);
    List<DishResponseDTO> listarTodos();
    void eliminarPlato(Long id);
}
