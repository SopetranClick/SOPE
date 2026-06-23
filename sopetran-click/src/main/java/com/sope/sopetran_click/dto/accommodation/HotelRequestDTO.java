package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDTO {

    private Long idAlojamiento; // ID de la categoría de alojamiento asociada
    private String nombre;
    private String direccion;
    private BigDecimal precioNoche;
    private String contacto;
}
