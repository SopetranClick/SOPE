package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDateRequestDTO {

    @NotNull(message = "You need to add the public entity ID.")
    private Long idPublicEntitie;

    @NotBlank(message = "The name can not be empty.")
    @Size(min = 3, max = 150, message = "The name must have between 3 and 150 letters.")
    private String nombre;

    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String descripcion;

    @NotNull(message = "You need to add the day.")
    @Min(1) @Max(31)
    private Integer dia;

    @NotNull(message = "You need to add the month.")
    @Min(1) @Max(12)
    private Integer mes;
}
