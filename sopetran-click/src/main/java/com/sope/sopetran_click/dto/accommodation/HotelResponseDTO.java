package com.sope.sopetran_click.dto.accommodation;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {

    private Long idHotel;
    private String name;
    private String address;
    private BigDecimal pricePerNight;
    private String contact;
    private String accommodationType; // Muestra directamente "Hotel", "Hostal" o "Glamping"
    private String coverUrl;           // imagen.txt portada (orden=0)
    private List<String> gallery;
    private String description;
}
