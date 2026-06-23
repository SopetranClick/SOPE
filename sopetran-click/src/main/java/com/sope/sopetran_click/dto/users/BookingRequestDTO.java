package com.sope.sopetran_click.dto.users;

import com.sope.sopetran_click.model.user.Booking;
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
    private Long idUser;            // Quién hace la reserva
    private Long idCategory;        // A qué categoría pertenece (alojamiento, trade, etc)
    private Long idItemEspecifico;  // ID del objeto real (Hotel ID, Local ID, etc)
    private Long idPayment;         // ID del pago

    // Detalles de la solicitud
    private LocalDateTime fechaEjecucion; // Fecha en que se hará efectiva la reserva
    private String description;           // Notas adicionales del cliente
    private BigDecimal montoTotal;        // Monto acordado
    private Booking.Status status;                // Estado de la reserva

}