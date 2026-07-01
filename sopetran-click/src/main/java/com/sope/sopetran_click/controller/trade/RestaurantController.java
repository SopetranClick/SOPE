package com.sope.sopetran_click.controller.trade;


import com.sope.sopetran_click.dto.trade.RestaurantRequestDTO;
import com.sope.sopetran_click.dto.trade.RestaurantResponseDTO;
import com.sope.sopetran_click.service.trade.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        List<RestaurantResponseDTO> restaurants = restaurantService.listarTodos();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id) {
        RestaurantResponseDTO restaurant = restaurantService.buscarPorId(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO restaurant = restaurantService.crearRestaurant(dto);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO restaurant = restaurantService.actualizarRestaurant(id, dto);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.eliminarRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
