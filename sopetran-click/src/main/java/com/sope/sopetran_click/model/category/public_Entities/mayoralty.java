package com.sope.sopetran_click.model.category.public_Entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "mayoralty")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class mayoralty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mayoralty")
    private Long idMayoralty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_public_entitie", referencedColumnName = "id_public_entitie")
    private Public_Entitie idPublicEntitie;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "contact", nullable = false)
    private String contact;

}
