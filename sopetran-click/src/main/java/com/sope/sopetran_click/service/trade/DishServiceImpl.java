package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.DishRequestDTO;
import com.sope.sopetran_click.dto.trade.DishResponseDTO;
import com.sope.sopetran_click.dto.trade.RestaurantResponseDTO;
import com.sope.sopetran_click.model.category.trade.Dish;
import com.sope.sopetran_click.model.category.trade.Restaurant;
import com.sope.sopetran_click.repository.DishRepository;
import com.sope.sopetran_click.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
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

    @Override
    public RestaurantResponseDTO buscarPorIdRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: restaurante no encontrado por ID"));
        return convertToResponseDTO(restaurant);
    }

    @Override
    public List<DishResponseDTO> listarPlatosPorRestaurante(Long restaurantId) {
        // 1. Validamos si el restaurante existe para lanzar una excepción amigable
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("No se pueden listar los platos: Restaurante no encontrado con ID: " + restaurantId);
        }

        // 2. Filtramos de manera segura todos los platos asociados a ese restaurante
        return dishRepository.findByRestaurantIdRestaurant(restaurantId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private DishResponseDTO convertToResponseDTO(Dish dish) {
        DishResponseDTO response = new DishResponseDTO();
        response.setIdDish(dish.getIdDish());
        response.setName(dish.getName());
        response.setPrice(dish.getPrice());
        response.setDescription(dish.getDescription());
        response.setRestaurantName(dish.getRestaurant().getName());
        response.setIsAvailable(dish.getIsAvailable());
        response.setImageUrl(dish.getImageUrl());
        response.setIdRestaurant(dish.getRestaurant().getIdRestaurant());

        // Obtenemos de forma segura la información de la categoría padre
        if (dish.getRestaurant() != null) {
            response.setIdRestaurant(dish.getRestaurant().getIdRestaurant());
            response.setRestaurantName(dish.getRestaurant().getName());
        }
        return response;
    }

    private RestaurantResponseDTO convertToResponseDTO(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setIdRestaurant(restaurant.getIdRestaurant());
        response.setNombre(restaurant.getName());
        response.setContacto(restaurant.getContact());

        // Mapea aquí cualquier otro campo que tengas definido en tu RestaurantResponseDTO
        return response;
    }
}
