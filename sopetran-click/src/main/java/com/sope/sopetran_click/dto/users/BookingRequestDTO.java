package com.sope.sopetran_click.dto.users;

import com.sope.sopetran_click.model.user.Booking;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO utilizado para recibir los datos de una nueva reserva desde el cliente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    // Identificadores de las relaciones
    @NotNull(message = "You need to add the user ID.")
    private Long idUser;            // Quién hace la reserva

    @NotNull(message = "You need to add the category ID.")
    private Long idCategory;        // A qué categoría pertenece (alojamiento, trade, etc)

    @NotNull(message = "You need to add the item ID.")
    private Long idItemEspecifico;  // ID del objeto real (Hotel ID, Local ID, etc)

    private Long idPayment;         // ID del pago (puede ser nulo al crear la reserva)

    // Detalles de la solicitud
    @NotNull(message = "You need to add the booking date.")
    @Future(message = "The booking date must be a future date.")
    private LocalDateTime fechaEjecucion; // Fecha en que se hará efectiva la reserva

    @Size(max = 500, message = "The description can not have more than 500 letters.")
    private String description;           // Notas adicionales del cliente

    @NotNull(message = "You need to add the total amount.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The total amount must be more than 0.")
    private BigDecimal montoTotal;        // Monto acordado

    @NotNull(message = "You need to add the booking status.")
    private Booking.Status status;                // Estado de la reserva

}
