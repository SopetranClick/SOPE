package com.sope.sopetran_click.dto.accommodation;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {

    private Long idHotel;
    private String nombre;
    private String direccion;
    private BigDecimal precioNoche;
    private String contacto;
    private String nombreAlojamiento; // Muestra directamente "Hotel", "Hostal" o "Glamping"
}
