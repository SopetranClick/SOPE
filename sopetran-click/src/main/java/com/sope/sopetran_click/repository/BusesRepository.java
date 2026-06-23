package com.sope.sopetran_click.repository;


import com.sope.sopetran_click.model.category.transport.Buses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusesRepository extends JpaRepository<Buses, Long> {
    // Para registrar y consultar líneas de buses (rutas Sopetrán - Medellín)
}