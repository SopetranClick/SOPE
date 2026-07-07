package com.sope.sopetran_click.dto.transport;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusesResponseDTO {
    private Long idBus;
    private String empresa;
    private String ruta;
    private String horarios;
    private String origen;
    private String destino;
    private BigDecimal precio;
    private String duracion;
    private Integer asientos;
}