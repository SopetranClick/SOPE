package com.sope.sopetran_click.dto.transport;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponseDTO {
    private Long idDriver;
    private Long idTransporte;
    private String nombre;
    private String placa;
    private String marca;
    private Integer anio;
    private String telefono;
    private Boolean disponible;
    private String tipoVehiculo;
}
