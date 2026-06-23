package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IconicPlaceResponseDTO {
    private Long idLugar;
    private String nombreLugar;
    private String indicaciones;
}