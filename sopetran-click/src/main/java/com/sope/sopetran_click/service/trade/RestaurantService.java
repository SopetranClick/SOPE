package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.RestaurantRequestDTO;
import com.sope.sopetran_click.dto.trade.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {

    RestaurantResponseDTO crearRestaurant(RestaurantRequestDTO dto);
    RestaurantResponseDTO actualizarRestaurant(Long id, RestaurantRequestDTO dto);
    RestaurantResponseDTO buscarPorId(Long id);
    List<RestaurantResponseDTO> listarTodos();
    void eliminarRestaurant(Long id);
}
