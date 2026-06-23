package com.sope.sopetran_click.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsRequestDTO {
    private Long idUser; // FK a Users
    private Long idCategory; // FK a Categorys
    private Long idItemEspecifico; // ID del negocio u hospedaje calificado
    private String texto;
    private Integer calificacion; // 1 a 5
}
