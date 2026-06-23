package com.sope.sopetran_click.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDTO {
    private Long idDish;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;
    private String imageUrl;
    private String restaurantName;
}
