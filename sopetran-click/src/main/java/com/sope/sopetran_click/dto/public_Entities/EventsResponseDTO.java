package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsResponseDTO {
    private Long idEvent;
    private String nombreEvento;
    private LocalDateTime fechaEvento;
    private String lugar;
    private String publicEntitieDescription;
    private String categoria;
    private Boolean featured;
    private String descripcionLarga;
    private String coverUrl;
    private List<String> gallery;
}