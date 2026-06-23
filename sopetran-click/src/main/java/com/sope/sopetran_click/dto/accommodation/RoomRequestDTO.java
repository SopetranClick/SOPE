package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {
    private Long idHotel; // FK a Hotels
    private String roomType; // 'Estándar', 'Doble', 'Suite'
    private Integer capacity;
    private BigDecimal pricePerNight;
    private Boolean isAvailable;
    private String descripcion;
}