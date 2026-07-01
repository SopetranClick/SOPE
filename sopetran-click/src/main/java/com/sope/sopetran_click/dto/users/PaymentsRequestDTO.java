package com.sope.sopetran_click.dto.users;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsRequestDTO {

    @NotNull(message = "You need to add the user ID.")
    private Long idUser; // FK a Users

    @NotNull(message = "You need to add the category ID.")
    private Long idCategory; // FK a Categorys

    private Long idBooking; // FK a Booking (puede ser nulo si no requiere reserva)

    @NotNull(message = "You need to add the cost.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The cost must be more than 0.")
    private BigDecimal cost;

    @NotBlank(message = "You need to add the payment method.")
    @Size(max = 30, message = "The payment method can not have more than 30 letters.")
    private String paymentMethod; // 'nequi', 'daviplata', 'tarjeta', etc.
}
