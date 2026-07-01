package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicEntitieRequestDTO {

    @NotNull(message = "You need to add the category ID.")
    private Long idCategory; // Relación con Categorys

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String descripcion;
}
