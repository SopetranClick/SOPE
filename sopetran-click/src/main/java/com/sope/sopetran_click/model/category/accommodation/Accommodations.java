package com.sope.sopetran_click.model.category.accommodation;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.sope.sopetran_click.model.Categorys;

@Entity
@Table(name = "accommodation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Accommodations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accommodation")
    private Long idAccommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categorys idCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
