package com.sope.sopetran_click.model.category.accommodation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * HotelImage — Tabla de imágenes de galería para un hotel.
 * Relación: Hotel 1 → N HotelImage
 *
 * Esta tabla se agrega con ddl-auto=update sin borrar la BD existente.
 * La primera imagen.txt (orden=0) actúa como portada (cover).
 */
@Entity
@Table(name = "hotel_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotels hotel;

    // Ruta relativa en /static/img/hoteles/ o URL externa
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    // 0 = portada, 1,2,3... = galería
    @Column(name = "orden", nullable = false)
    private Integer orden = 0;

    @Column(name = "alt_text", length = 200)
    private String altText;
}