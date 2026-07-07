package com.sope.sopetran_click.model.category.trade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Long idLocal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trades", referencedColumnName = "id_trades")
    private Trades idTrades;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "type_local")
    private String type_local;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "horario")
    private String horario;

    @Column(name = "abierto")
    private Boolean abierto = true;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<LocalImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(LocalImage::getUrl)
                .findFirst()
                .orElse("/img/placeholder-local.jpg");
    }

}
