package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDTO {

    @NotNull(message = "You need to add the public entity ID.")
    private Long idPublicEntitie; // Relación con Public_Entitie

    @NotBlank(message = "The title can not be empty.")
    @Size(min = 3, max = 150, message = "The title must have between 3 and 150 letters.")
    private String titulo;

    @NotBlank(message = "The content can not be empty.")
    @Size(max = 2000, message = "The content can not have more than 2000 letters.")
    private String contenido;

    @NotBlank(message = "The date can not be empty.")
    private String fechaPublicacion;
}
