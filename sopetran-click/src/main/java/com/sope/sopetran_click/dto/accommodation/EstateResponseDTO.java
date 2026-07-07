package com.sope.sopetran_click.dto.accommodation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstateResponseDTO {
    private Long idEstate;
    private String name;
    private String farmType;
    private String description;
    private String location;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private String imageUrl;
    private String contact;
    private String coverUrl;       // imagen.txt portada (orden=0)
    private List<String> gallery;
}
