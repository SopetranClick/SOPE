package com.sope.sopetran_click.dto.transport;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotorbikeRequestDTO {

    @NotNull(message = "You need to add the transport ID.")
    private Long idTransporte; // FK a Transports

    @NotBlank(message = "The association name can not be empty.")
    @Size(min = 3, max = 100, message = "The association name must have between 3 and 100 letters.")
    private String asociacionNombre;

    @NotBlank(message = "You need to add the coverage zone.")
    @Size(max = 150, message = "The coverage zone can not have more than 150 letters.")
    private String zonaCobertura;

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String contacto;
}
