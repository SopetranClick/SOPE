package com.sope.sopetran_click.model.category.trade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long idRestaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trades", referencedColumnName = "id_trades")
    private Trades idTrades;



    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "horario")
    private String horario;

    @Column(name = "abierto")
    private Boolean abierto = true;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<RestaurantImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(RestaurantImage::getUrl)
                .findFirst()
                .orElse("/img/placeholder-restaurante.jpg");
    }



}
