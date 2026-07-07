package com.sope.sopetran_click.dto.ecotourism;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteResponseDTO {
    private Long idVereda;
    private String nombreVereda;
    private String descripcion;
    private String tags;
    private String coverUrl;
    private List<String> gallery;
}