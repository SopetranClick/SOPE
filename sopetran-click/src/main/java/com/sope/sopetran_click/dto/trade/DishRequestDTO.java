package com.sope.sopetran_click.dto.trade;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {

    @NotNull(message = "You need to add the restaurant ID.")
    private Long idRestaurant; // FK a Restaurant

    @NotBlank(message = "The dish name can not be empty.")
    @Size(min = 2, max = 100, message = "The dish name must have between 2 and 100 letters.")
    private String name;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String description;

    @NotNull(message = "You need to add the price.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be more than 0.")
    private BigDecimal price;

    @NotNull(message = "You need to say if the dish is available.")
    private Boolean isAvailable;

    @NotBlank(message = "You need to add the image.")
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "The image link is not correct (example: http://... or https://...).")
    private String imageUrl;
}
