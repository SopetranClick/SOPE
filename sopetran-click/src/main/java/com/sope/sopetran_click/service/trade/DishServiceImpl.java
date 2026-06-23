package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.DishRequestDTO;
import com.sope.sopetran_click.dto.trade.DishResponseDTO;
import com.sope.sopetran_click.model.category.trade.Dish;
import com.sope.sopetran_click.model.category.trade.Restaurant;
import com.sope.sopetran_click.repository.DishRepository;
import com.sope.sopetran_click.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class DishServiceImpl implements DishService{

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public DishResponseDTO crearPlato(DishRequestDTO dto) {

        Restaurant restaurant = restaurantRepository.findById(dto.getIdRestaurant())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " ));

        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setPrice(dto.getPrice());
        dish.setRestaurant(restaurant);
        dish.setIsAvailable(dto.getIsAvailable());
        dish.setDescription(dto.getDescription());
        dish.setImageUrl(dto.getImageUrl());

        Dish dishguardado = dishRepository.save(dish);
        return convertToResponseDTO(dishguardado);
    }

    @Override
    public DishResponseDTO actualizarPlato(Long id, DishRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getIdRestaurant())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " ));

        Dish dishexiste = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: plato no encontrado por ID"));

        dishexiste.setName(dto.getName());
        dishexiste.setPrice(dto.getPrice());
        dishexiste.setRestaurant(restaurant);
        dishexiste.setDescription(dto.getDescription());
        dishexiste.setImageUrl(dto.getImageUrl());

        Dish dishActualizado = dishRepository.save(dishexiste);

        return convertToResponseDTO(dishActualizado);
    }

    @Override
    public DishResponseDTO buscarPorId(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: plato no encontrado por ID"));
        return convertToResponseDTO(dish);
    }

    @Override
    public List<DishResponseDTO> listarTodos() {
        return dishRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarPlato(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: plato no encontrado por ID");
        }
        dishRepository.deleteById(id);
    }

    private DishResponseDTO convertToResponseDTO(Dish dish) {
        DishResponseDTO response = new DishResponseDTO();
        response.setIdDish(dish.getIdDish());
        response.setName(dish.getName());
        response.setPrice(dish.getPrice());
        response.setDescription(dish.getDescription());
        response.setRestaurantName(dish.getRestaurant().getName());
        response.setIsAvailable(dish.getIsAvailable());

        // Obtenemos de forma segura la información de la categoría padre
        if (dish.getRestaurant() != null) {
            response.setRestaurantName(dish.getRestaurant().getName());
        }
        return response;
    }
}
