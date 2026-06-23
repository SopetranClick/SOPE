package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IconicPlaceRequestDTO {
    private Long idEcoturismo; // FK a Ecotourism
    private String nombreLugar;
    private String indicaciones;
}