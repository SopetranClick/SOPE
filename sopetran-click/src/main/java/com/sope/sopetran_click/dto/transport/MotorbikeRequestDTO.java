package com.sope.sopetran_click.dto.transport;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotorbikeRequestDTO {
    private Long idTransporte; // FK a Transports
    private String asociacionNombre;
    private String zonaCobertura;
    private String contacto;
}