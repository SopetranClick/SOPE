package com.sope.sopetran_click.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsRequestDTO {
    private Long idUser; // FK a Users
    private Long idCategory; // FK a Categorys
    private Long idBooking; // FK a Booking (puede ser nulo si no requiere reserva)
    private BigDecimal cost;
    private String paymentMethod; // 'nequi', 'daviplata', 'tarjeta', etc.
}