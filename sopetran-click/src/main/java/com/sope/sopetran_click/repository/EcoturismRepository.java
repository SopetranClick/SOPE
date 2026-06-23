package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.ecotourism.Ecotourism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoturismRepository extends JpaRepository<Ecotourism, Long> {
}