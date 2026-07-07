package com.sope.sopetran_click.model.category.ecotourism;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "tags")
    private String tags;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<SiteImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(SiteImage::getUrl)
                .findFirst()
                .orElse("/img/placeholder-vereda.jpg");
    }



}
