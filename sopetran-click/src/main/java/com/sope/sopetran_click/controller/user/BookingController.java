package com.sope.sopetran_click.controller.user;


import com.sope.sopetran_click.dto.users.BookingRequestDTO;
import com.sope.sopetran_click.dto.users.BookingResponseDTO;
import com.sope.sopetran_click.service.user.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    /** Lista todas las reservas de un usuario */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.findAllByUserId(userId));
    }

    /** Lista reservas por tipo de categoría (ej: "HOTEL", "RESTAURANT") */
    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByCategory(
            @PathVariable String categoryType) {
        return ResponseEntity.ok(bookingService.findAllByCategoryId(categoryType));
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO dto) {
        return new ResponseEntity<>(bookingService.createBooking(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(
            @PathVariable Long id, @Valid @RequestBody BookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
