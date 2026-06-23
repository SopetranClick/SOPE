package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstateRequestDTO {
    private Long idAccommodation; // ID de la relación con Accommodations
    private String nombre;
    private String tipoFinca; // 'finca recreativa', 'casa campestre'
    private String descripcion;
    private String ubicacion;
    private BigDecimal precioPorNoche;
    private Integer capacidad;
    private String imageUrl;
    private String contacto;
}