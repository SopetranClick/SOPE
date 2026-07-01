package com.sope.sopetran_click.dto.transport;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusesRequestDTO {

    @NotNull(message = "You need to add the transport ID.")
    private Long idTransporte; // FK a Transports

    @NotBlank(message = "The company name can not be empty.")
    @Size(min = 3, max = 100, message = "The company name must have between 3 and 100 letters.")
    private String empresa;

    @NotBlank(message = "You need to add the route.")
    @Size(max = 150, message = "The route can not have more than 150 letters.")
    private String ruta;

    @NotBlank(message = "You need to add the schedule.")
    @Size(max = 150, message = "The schedule can not have more than 150 letters.")
    private String horarios;
}
