package com.sope.sopetran_click.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalResponseDTO {
    private Long idLocal;
    private String nombre;
    private String tipoLocal;
    private String direccion;
    private String contacto;
    private Long idTrade;
    private String description;
    private String categoria;
    private Double rating;
    private String horario;
    private Boolean abierto;
    private String coverUrl;
    private List<String> gallery;
}