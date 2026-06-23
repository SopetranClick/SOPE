package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteRequestDTO {
    private Long idEcoturismo; // FK a Ecotourism
    private String nombreVereda;
    private String descripcion;
}