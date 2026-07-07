package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IconicPlaceResponseDTO {
    private Long idLugar;
    private String nombreLugar;
    private String indicaciones;
    private String tipo;
    private String acceso;
    private String tags;
    private String coverUrl;
    private List<String> gallery;
    private Long idVereda;
}