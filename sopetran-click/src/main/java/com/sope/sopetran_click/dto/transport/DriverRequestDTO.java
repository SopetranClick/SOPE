package com.sope.sopetran_click.dto.transport;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequestDTO {

    @NotNull(message = "You need to add the transport ID.")
    private Long idTransporte; // FK a Transports

    @NotBlank(message = "The name can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String nombre;

    private String placa;
    private String marca;
    private Integer anio;

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String telefono;

    private Boolean disponible;

    @NotBlank(message = "You need to add the vehicle type.")
    private String tipoVehiculo; // "MOTO" | "CARRO"
}
