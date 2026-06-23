package com.sope.sopetran_click.model.category.transport;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Buses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_buses")
    private Long idBuses;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transports", referencedColumnName = "id_transports")
    private Transports idTransports;

    @Column(name = "description")
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "route", nullable = false)
    private String route;

    @Column(name = "schedule", nullable = false)
    private String schedule;

}
