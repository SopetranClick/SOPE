package com.sope.sopetran_click.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {
    private Long idRestaurant; // FK a Restaurant
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;
    private String imageUrl;
}
