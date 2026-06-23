package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.BookingRequestDTO;
import com.sope.sopetran_click.dto.users.BookingResponseDTO;
import com.sope.sopetran_click.model.user.Booking;
import java.util.List;

public interface BookingService {

    /**
     * Crea una nueva reserva.
     */
    BookingResponseDTO createBooking(BookingRequestDTO dto);

    /**
     * Actualiza una reserva existente.
     */
    BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto);

    /**
     * Elimina una reserva por su ID.
     */
    void deleteBooking(Long id);

    /**
     * Busca una reserva específica por su ID.
     */
    BookingResponseDTO findById(Long id);

    /**
     * Busca todas las reservas de un usuario.
     */
    List<BookingResponseDTO> findAllByUserId(Long userId);

    /**
     * Busca reservas filtradas por el ID de la categoría (ej: Hoteles, Transporte, etc.)
     */
    List<BookingResponseDTO> findAllByCategoryId(String categoryId);
}