package com.sope.sopetran_click.model.category.ecotourism;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "site")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_site")
    private Long idSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ecotourism", referencedColumnName = "id_ecotourism")
    private Ecotourism idEcotourism;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "name", nullable = false)
    private String name;



}
