package com.sope.sopetran_click.model.category.accommodation;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hotel")
    private Long idHotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_accommodation", referencedColumnName = "id_accommodation")
    private Accommodations idAccommodation;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "contact", nullable = false)
    private String Contact;

}
