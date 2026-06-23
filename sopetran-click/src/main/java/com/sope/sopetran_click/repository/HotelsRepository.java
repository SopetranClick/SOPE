package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.accommodation.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Long> {
    // Métodos CRUD básicos para la gestión hotelera
}
