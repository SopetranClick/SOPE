package com.sope.sopetran_click.dto.ecotourism;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class IconicPlaceRequestDTO {

    @NotNull(message = "You need to add the ecotourism ID.")
    private Long idEcoturismo; // FK a Ecotourism

    @NotNull(message = "You need to add the site ID.")
    private Long idLugar; // FK a Site

    @NotBlank(message = "The name of the place can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String nombreLugar;

    @NotBlank(message = "You need to add directions to the place.")
    @Size(max = 1000, message = "The directions can not have more than 1000 letters.")
    private String indicaciones;

    private String tipo;
    private String acceso;
    private String tags;
}
