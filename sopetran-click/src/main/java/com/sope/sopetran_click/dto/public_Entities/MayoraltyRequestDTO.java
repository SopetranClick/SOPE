package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MayoraltyRequestDTO {
    private Long idPublicEntitie; // Relación con Public_Entitie
    private String dependencia; // Ej. "Planeación", "Hacienda"
    private String contacto;
}