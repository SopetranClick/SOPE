package com.sope.sopetran_click.controller.trade;


import com.sope.sopetran_click.dto.trade.DishRequestDTO;
import com.sope.sopetran_click.dto.trade.DishResponseDTO;
import com.sope.sopetran_click.dto.trade.RestaurantResponseDTO;
import com.sope.sopetran_click.service.trade.DishService;
import com.sope.sopetran_click.service.trade.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    private final RestaurantService restaurantService;

    public DishController(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getAllDishes( ) {
        List<DishResponseDTO> dishes = dishService.listarTodos();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDish(@PathVariable Long id) {
        DishResponseDTO dish = dishService.buscarPorId(id);
        return ResponseEntity.ok(dish);
    }

    @PostMapping
    public ResponseEntity<DishResponseDTO> createDish(@Valid @RequestBody DishRequestDTO dto) {
        DishResponseDTO dish = dishService.crearPlato(dto);
        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Long id, @Valid @RequestBody DishRequestDTO dto) {
        DishResponseDTO dish = dishService.actualizarPlato(id, dto);
        return ResponseEntity.ok(dish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<DishResponseDTO>> getDishesByRestaurant(@PathVariable Long id) {
        // LLAMADA CORRECTA: Llama al servicio de platos para obtener la lista de platos de ese restaurante
        List<DishResponseDTO> dishes = dishService.listarPlatosPorRestaurante(id);
        return ResponseEntity.ok(dishes);
    }



}
