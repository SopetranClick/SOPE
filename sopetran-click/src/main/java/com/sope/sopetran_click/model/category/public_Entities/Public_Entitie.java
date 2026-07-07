package com.sope.sopetran_click.model.category.public_Entities;


import com.sope.sopetran_click.model.Categorys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "public_entitie")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Public_Entitie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_public_entitie")
    private Long idPublicEntitie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categorys idCategory;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
}
