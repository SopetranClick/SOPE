package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private Long idRoom;
    private String roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private Boolean isAvailable;
    private String descripcion;
    private String hotelName; // Para mostrar a qué hotel pertenece en la vista
}