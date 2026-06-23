package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.trade.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    // Obtener la carta de platos de un restaurante específico
    List<Dish> findByRestaurantIdRestaurant(Long idRestaurant);

    // Filtrar platos por disponibilidad en cocina
    List<Dish> findByIsAvailableTrue();
}
