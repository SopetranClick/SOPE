package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.user.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de persistencia de las reservas.
 * Extiende JpaRepository para obtener automáticamente métodos CRUD básicos.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Filtra las reservas por el tipo de categoría especificado.
     * * @param categoryType El nombre o código de la categoría (ej: "ACCOMMODATION", "TRANSPORT").
     * @return Una lista de objetos Booking que coinciden con el tipo dado.
     */
    List<Booking> findByCategoryType(String categoryType);
    List<Booking> findAllByIdUsers_Id(Long userId);
}