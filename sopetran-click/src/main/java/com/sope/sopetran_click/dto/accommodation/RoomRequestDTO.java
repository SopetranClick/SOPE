package com.sope.sopetran_click.dto.accommodation;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {

    @NotNull(message = "You need to add the hotel ID.")
    private Long idHotel; // FK a Hotels

    @NotBlank(message = "You need to add the room type.")
    @Size(max = 50, message = "The room type can not have more than 50 letters.")
    private String roomType; // 'Estándar', 'Doble', 'Suite'

    @NotNull(message = "You need to add the room capacity.")
    @Min(value = 1, message = "The room must have space for at least 1 person.")
    @Max(value = 50, message = "The room can not have more than 50 people.")
    private Integer capacity;

    @NotNull(message = "You need to add the price per night.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be more than 0.")
    private BigDecimal pricePerNight;

    @NotNull(message = "You need to say if the room is available.")
    private Boolean isAvailable;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String descripcion;
}
