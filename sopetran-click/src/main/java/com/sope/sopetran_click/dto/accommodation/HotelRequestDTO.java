package com.sope.sopetran_click.dto.accommodation;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDTO {

    @NotNull(message = "You need to add the accommodation ID.")
    private Long idAccommodation; // ID de la categoría de alojamiento asociada

    @NotBlank(message = "The name can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String name;

    @NotBlank(message = "You need to add the address.")
    @Size(max = 255, message = "The address can not have more than 255 letters.")
    private String address;

    @NotNull(message = "You need to add the price per night.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be more than 0.")
    private BigDecimal pricePerNight;

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String contact;

    @Size(max = 255, message = "The description can not have more than 255 characters.")
    private String description;
}
