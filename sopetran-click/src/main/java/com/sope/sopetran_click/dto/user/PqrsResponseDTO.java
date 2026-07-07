package com.sope.sopetran_click.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PqrsResponseDTO {
    private Long idPqrs;
    private String tipo;
    private String nombre;
    private String asunto;
    private String estado;
    private LocalDateTime creadoEn;
}
