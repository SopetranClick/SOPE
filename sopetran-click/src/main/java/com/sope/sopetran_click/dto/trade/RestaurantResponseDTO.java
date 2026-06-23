package com.sope.sopetran_click.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDTO {
    private Long idRestaurant;
    private String nombre;
    private String contacto;
    private String description;
}