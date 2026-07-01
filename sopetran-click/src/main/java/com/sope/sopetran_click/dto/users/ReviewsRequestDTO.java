package com.sope.sopetran_click.dto.users;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsRequestDTO {

    @NotNull(message = "You need to add the user ID.")
    private Long idUser; // FK a Users

    @NotNull(message = "You need to add the category ID.")
    private Long idCategory; // FK a Categorys

    @NotNull(message = "You need to add the item ID.")
    private Long idItemEspecifico; // ID del negocio u hospedaje calificado

    @NotBlank(message = "The review text can not be empty.")
    @Size(max = 500, message = "The review text can not have more than 500 letters.")
    private String texto;

    @NotNull(message = "You need to add a rating.")
    @Min(value = 1, message = "The rating must be at least 1.")
    @Max(value = 5, message = "The rating can not be more than 5.")
    private Integer calificacion; // 1 a 5
}
