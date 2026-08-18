package com.sope.sopetran_click.model.category.accommodation;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estate")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Estate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estate")
    private Long idEstate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_accommodation", referencedColumnName = "id_accommodation")
    private Accommodations idAccommodation;


    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @Column(name = "description", nullable = false, length = 255)
    private String description;


    @Column(name = "type_estate")
    private String typeEstate;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "price" , nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "estate", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<EstateImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(EstateImage::getUrl)
                .findFirst()
                .orElse("/img/PRINCI/carrusel1.jpg");
    }

}
