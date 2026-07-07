package com.sope.sopetran_click.model.category.transport;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_driver")
    private Long idDriver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transports", referencedColumnName = "id_transports")
    private Transports idTransports;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "placa")
    private String placa;

    @Column(name = "marca")
    private String marca;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "disponible")
    private Boolean disponible = true;

    @Column(name = "tipo_vehiculo", nullable = false)
    private String tipoVehiculo; // "MOTO" | "CARRO"
}
