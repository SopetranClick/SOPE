package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.RestaurantRequestDTO;
import com.sope.sopetran_click.dto.trade.RestaurantResponseDTO;
import com.sope.sopetran_click.model.category.trade.Restaurant;
import com.sope.sopetran_click.model.category.trade.Trades;
import com.sope.sopetran_click.repository.RestaurantRepository;
import com.sope.sopetran_click.repository.TradesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private TradesRepository tradesRepository;

    @Override
    public RestaurantResponseDTO crearRestaurant(RestaurantRequestDTO dto) {
        Trades trade = tradesRepository.findById(dto.getIdTrade())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getNombre());
        restaurant.setDescription(dto.getDescription());
        restaurant.setContact(dto.getContacto());
        restaurant.setIdTrades(trade);
        restaurant.setRating(dto.getRating());
        restaurant.setHorario(dto.getHorario());
        restaurant.setAbierto(dto.getAbierto() != null ? dto.getAbierto() : true);

        Restaurant restaurantGuardado = restaurantRepository.save(restaurant);

        return convertToResponseDTO(restaurantGuardado);
    }

    @Override
    public RestaurantResponseDTO actualizarRestaurant(Long id, RestaurantRequestDTO dto) {
        Restaurant restaurantexiste = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Restaurant no encontrado por ID"));

        Trades trade = tradesRepository.findById(dto.getIdTrade())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        restaurantexiste.setName(dto.getNombre());
        restaurantexiste.setDescription(dto.getDescription());
        restaurantexiste.setContact(dto.getContacto());
        restaurantexiste.setIdTrades(trade);
        restaurantexiste.setRating(dto.getRating());
        restaurantexiste.setHorario(dto.getHorario());
        restaurantexiste.setAbierto(dto.getAbierto() != null ? dto.getAbierto() : true);

        Restaurant restaurantActualizado = restaurantRepository.save(restaurantexiste);

        return convertToResponseDTO(restaurantActualizado);
    }

    @Override
    public RestaurantResponseDTO buscarPorId(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro el Restaurant"));
        return convertToResponseDTO(restaurant);
    }

    @Override
    public List<RestaurantResponseDTO> listarTodos() {

        return restaurantRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Restaurant no encontrado por ID");
        }
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponseDTO convertToResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setIdRestaurant(restaurant.getIdRestaurant());
        response.setNombre(restaurant.getName());
        response.setDescription(restaurant.getDescription());
        response.setContacto(restaurant.getContact());
        response.setCategoria("restaurante");
        response.setRating(restaurant.getRating());
        response.setHorario(restaurant.getHorario());
        response.setAbierto(restaurant.getAbierto());

        if (restaurant.getIdTrades() != null) {
            response.setIdTrade(restaurant.getIdTrades().getIdTrades());
        }

        if (restaurant.getImagenes() != null && !restaurant.getImagenes().isEmpty()) {
            response.setCoverUrl(restaurant.getCoverUrl());
            response.setGallery(
                    restaurant.getImagenes().stream()
                            .filter(i -> i.getOrden() > 0)
                            .map(i -> i.getUrl())
                            .collect(Collectors.toList())
            );
        } else {
            response.setCoverUrl(restaurant.getCoverUrl());
            response.setGallery(Collections.emptyList());
        }
        return response;
    }
}
