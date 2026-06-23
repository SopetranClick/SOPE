package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.ecotourism.Iconic_place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconicPlaceRepository extends JpaRepository<Iconic_place, Long> {
    // Permite gestionar miradores, cascadas y puentes históricos de Sopetrán
}
