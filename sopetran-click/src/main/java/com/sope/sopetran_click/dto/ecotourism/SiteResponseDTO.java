package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteResponseDTO {
    private Long idVereda;
    private String nombreVereda;
    private String descripcion;
}