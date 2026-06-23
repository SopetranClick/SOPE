package com.sope.sopetran_click.model.category.ecotourism;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "iconic_place")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Iconic_place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iconic_place")
    private Long idIconicPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_site", referencedColumnName = "id_site")
    private Site idsite;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description" , nullable = false)
    private String description;

}
