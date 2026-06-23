package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsResponseDTO {
    private Long idEvent;
    private String nombreEvento;
    private LocalDateTime fechaEvento;
    private String lugar;
    private String publicEntitieDescription;
}