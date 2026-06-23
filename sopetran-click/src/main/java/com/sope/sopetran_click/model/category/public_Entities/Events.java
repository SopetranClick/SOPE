package com.sope.sopetran_click.model.category.public_Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long idEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_public_entitie", referencedColumnName = "id_public_entitie")
    private Public_Entitie idPublicEntitie;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "venues", nullable = false)
    private String venues;
}
