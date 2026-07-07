package com.sope.sopetran_click.model.category.transport;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "motorbike")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Motorbike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorbike")
    private Long idMotorbike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transports", referencedColumnName = "id_transports")
    private Transports idTransports;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "zona_cobertura")
    private String zonaCobertura;

}
