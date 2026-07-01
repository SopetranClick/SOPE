package com.sope.sopetran_click.dto.trade;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDTO {

    @NotNull(message = "You need to add the trade ID.")
    private Long idTrade; // FK a Trades

    @NotBlank(message = "The name can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String nombre;

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String contacto;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String description;
}
