package com.sope.sopetran_click.dto.transport;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusesResponseDTO {
    private Long idBus;
    private String empresa;
    private String ruta;
    private String horarios;
}