package com.sope.sopetran_click.model.category.ecotourism;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "iconic_place_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IconicPlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_iconic_place", nullable = false)
    private Iconic_place iconicPlace;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "orden", nullable = false)
    private Integer orden = 0;

    @Column(name = "alt_text", length = 200)
    private String altText;
}
