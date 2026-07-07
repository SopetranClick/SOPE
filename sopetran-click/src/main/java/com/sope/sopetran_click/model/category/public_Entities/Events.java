package com.sope.sopetran_click.model.category.public_Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long idEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_public_entitie", referencedColumnName = "id_public_entitie")
    private Public_Entitie idPublicEntitie;

    @Column(name = "description", nullable = false , columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "venues", nullable = false)
    private String venues;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "descripcion_larga", columnDefinition = "TEXT")
    private String descripcionLarga;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<EventImage> imagenes = new ArrayList<>();

    @Transient
    public String getCoverUrl() {
        return imagenes.stream()
                .filter(i -> i.getOrden() == 0)
                .map(EventImage::getUrl)
                .findFirst()
                .orElse("/img/placeholder-evento.jpg");
    }
}
