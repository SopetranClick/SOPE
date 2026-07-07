package com.sope.sopetran_click.model.category.public_Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "history_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history_entry")
    private Long idHistoryEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_public_entitie", referencedColumnName = "id_public_entitie")
    private Public_Entitie idPublicEntitie;

    @Column(name = "era", nullable = false)
    private String era;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "texto", columnDefinition = "TEXT")
    private String texto;

    @Column(name = "numero")
    private String numero;

    @Column(name = "orden")
    private Integer orden = 0;

    @Column(name = "main")
    private Boolean main = false;
}
