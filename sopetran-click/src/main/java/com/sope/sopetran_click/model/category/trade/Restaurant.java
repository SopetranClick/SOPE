package com.sope.sopetran_click.model.category.trade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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



}
