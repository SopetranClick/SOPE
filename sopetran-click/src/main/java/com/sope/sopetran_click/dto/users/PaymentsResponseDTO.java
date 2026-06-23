package com.sope.sopetran_click.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsResponseDTO {
    private Long idPayment;
    private String userName;
    private String categoryName;
    private Long idBooking;
    private BigDecimal cost;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String status;
}
