package com.sope.sopetran_click.dto.ecotourism;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteRequestDTO {

    @NotNull(message = "You need to add the ecotourism ID.")
    private Long idEcoturismo; // FK a Ecotourism

    @NotBlank(message = "The name of the village can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String nombreVereda;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String descripcion;

    private String tags;
}
