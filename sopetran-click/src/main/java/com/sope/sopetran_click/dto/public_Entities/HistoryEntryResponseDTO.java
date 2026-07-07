package com.sope.sopetran_click.dto.public_Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntryResponseDTO {
    private Long idHistoryEntry;
    private Long idPublicEntitie;
    private String era;
    private String titulo;
    private String texto;
    private String numero;
    private Integer orden;
    private Boolean main;
}
