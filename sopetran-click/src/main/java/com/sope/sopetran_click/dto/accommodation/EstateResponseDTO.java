package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstateResponseDTO {
    private Long idEstate;
    private String nombre;
    private String tipoFinca;
    private String descripcion;
    private String ubicacion;
    private BigDecimal precioPorNoche;
    private Integer capacidad;
    private String imageUrl;
    private String contacto;
}
