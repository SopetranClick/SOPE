package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MayoraltyRequestDTO {

    @NotNull(message = "You need to add the public entity ID.")
    private Long idPublicEntitie; // Relación con Public_Entitie

    @NotBlank(message = "You need to add the department name.")
    @Size(max = 100, message = "The department name can not have more than 100 letters.")
    private String dependencia; // Ej. "Planeación", "Hacienda"

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String contacto;
}
