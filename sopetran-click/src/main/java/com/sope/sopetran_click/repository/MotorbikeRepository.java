package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.transport.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorbikeRepository extends JpaRepository<Motorbike, Long> {
    // Para cooperativas de mototaxistas y alquiler de motos
    List<Motorbike> findByNameContainingIgnoreCase(String name);
    List<Motorbike> findByIdTransports_IdTransports(Long idTransports);
}