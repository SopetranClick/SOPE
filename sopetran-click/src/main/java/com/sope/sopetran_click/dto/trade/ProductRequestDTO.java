package com.sope.sopetran_click.dto.trade;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "You need to add the local ID.")
    private Long idLocal; // FK a Local

    @NotBlank(message = "The product name can not be empty.")
    @Size(min = 2, max = 100, message = "The product name must have between 2 and 100 letters.")
    private String name;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String description;

    @NotNull(message = "You need to add the price.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be more than 0.")
    private BigDecimal price;

    @NotNull(message = "You need to add the stock.")
    @Min(value = 0, message = "The stock can not be a negative number.")
    private Integer stock;

    @NotBlank(message = "You need to add the image.")
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "The image link is not correct (example: http://... or https://...).")
    private String imageUrl;

    @NotNull(message = "You need to say if the product is active.")
    private Boolean isActive;
}
