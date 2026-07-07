package com.sope.sopetran_click.model.category.public_Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "special_date")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_special_date")
    private Long idSpecialDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_public_entitie", referencedColumnName = "id_public_entitie")
    private Public_Entitie idPublicEntitie;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "dia", nullable = false)
    private Integer dia;

    @Column(name = "mes", nullable = false)
    private Integer mes;
}
