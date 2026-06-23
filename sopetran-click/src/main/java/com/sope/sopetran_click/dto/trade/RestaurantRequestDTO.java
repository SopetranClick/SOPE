package com.sope.sopetran_click.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDTO {
    private Long idTrade; // FK a Trades
    private String nombre;

    private String contacto;
    private String description;
}