package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicEntitieRequestDTO {
    private Long idCategory; // Relación con Categorys
    private String descripcion;
}