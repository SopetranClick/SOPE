package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.accommodation.Accommodations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationsRepository extends JpaRepository<Accommodations, Long> {
    // Hereda todos los métodos CRUD básicos
}