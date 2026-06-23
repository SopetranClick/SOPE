package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MayoraltyResponseDTO {
    private Long idMayoralty;
    private String dependencia;
    private String contacto;
    private String publicEntitieDescription; // Información de la entidad base
}