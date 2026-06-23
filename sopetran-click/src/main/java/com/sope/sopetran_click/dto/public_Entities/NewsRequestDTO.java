package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDTO {
    private Long idPublicEntitie; // Relación con Public_Entitie
    private String titulo;
    private String contenido;
}