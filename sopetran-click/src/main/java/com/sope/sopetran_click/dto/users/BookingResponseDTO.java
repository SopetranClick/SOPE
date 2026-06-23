package com.sope.sopetran_click.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO utilizado para enviar los detalles de la reserva al cliente (frontend).
 * Incluye información descriptiva y referencias necesarias.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {

    private Long idBooking;

    // Información del usuario
    private Long userId;
    private String userName; // Útil para mostrar quién hizo la reserva

    // Información del objetivo reservado (Hotel, Restaurante, etc.)
    private Long targetId;
    private String targetType; // Indica la categoría

    // Detalles de la reserva
    private LocalDateTime fechaReserva;
    private String description;
    private Double cost;
    private Enum status;

    // Fechas de control
    private LocalDateTime fechaCancelacion;
    private LocalDateTime fechaFinalizacion;

    // Información opcional del pago
    private Long paymentId;
    private String paymentStatus;
}