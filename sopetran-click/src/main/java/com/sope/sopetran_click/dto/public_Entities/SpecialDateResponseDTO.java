package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDateResponseDTO {
    private Long idSpecialDate;
    private Long idPublicEntitie;
    private String nombre;
    private String descripcion;
    private Integer dia;
    private Integer mes;
}
