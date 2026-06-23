package com.sope.sopetran_click.dto.transport;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotorbikeResponseDTO {
    private Long idMoto;
    private String asociacionNombre;
    private String zonaCobertura;
    private String contacto;
}