package com.sope.sopetran_click.model.category.ecotourism;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "acceso")
    private String acceso;

    @Column(name = "tags")
    private String tags;

    @OneToMany(mappedBy = "iconicPlace", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<IconicPlaceImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(IconicPlaceImage::getUrl)
                .findFirst()
                .orElse("/img/placeholder-lugar.jpg");
    }

}
