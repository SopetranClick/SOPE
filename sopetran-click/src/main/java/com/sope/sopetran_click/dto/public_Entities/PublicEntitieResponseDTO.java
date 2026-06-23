package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicEntitieResponseDTO {
    private Long idPublicEntitie;
    private String descripcion;
    private String nameCategory; // Nombre de la categoría asociada
}