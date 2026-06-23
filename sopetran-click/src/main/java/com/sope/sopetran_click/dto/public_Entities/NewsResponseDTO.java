package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {
    private Long idNews;
    private String titulo;
    private String contenido;
    private LocalDateTime fechaPublicacion;
    private String publicEntitieDescription;
}
