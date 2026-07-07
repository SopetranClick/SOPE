package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsRequestDTO {

    @NotNull(message = "You need to add the public entity ID.")
    private Long idPublicEntitie; // Relación con Public_Entitie

    @NotBlank(message = "The event name can not be empty.")
    @Size(min = 3, max = 150, message = "The event name must have between 3 and 150 letters.")
    private String nombreEvento;

    @NotNull(message = "You need to add the event date.")
    @Future(message = "The event date must be a future date.")
    private LocalDateTime fechaEvento;

    @NotBlank(message = "You need to add the event place.")
    @Size(max = 150, message = "The place can not have more than 150 letters.")
    private String lugar;

    private String categoria;
    private Boolean featured;
    private String descripcionLarga;
}
