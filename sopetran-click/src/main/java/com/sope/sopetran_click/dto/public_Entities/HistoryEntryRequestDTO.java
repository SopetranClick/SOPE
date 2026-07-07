package com.sope.sopetran_click.dto.public_Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntryRequestDTO {

    @NotNull(message = "You need to add the public entity ID.")
    private Long idPublicEntitie;

    @NotBlank(message = "The era can not be empty.")
    @Size(max = 100, message = "The era can not have more than 100 letters.")
    private String era;

    @NotBlank(message = "The title can not be empty.")
    @Size(min = 3, max = 150, message = "The title must have between 3 and 150 letters.")
    private String titulo;

    @Size(max = 2000, message = "The text can not have more than 2000 letters.")
    private String texto;

    private String numero;
    private Integer orden;
    private Boolean main;
}
