package com.sope.sopetran_click.model.category.ecotourism;


import com.sope.sopetran_click.model.Categorys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ecotourism")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ecotourism {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ecotourism")
    private Long idEcotourism;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categorys idCategory;

    @Column(name = "description")
    private String description;

}
