package com.sope.sopetran_click.model.category.accommodation;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones") // Nombre de la tabla en PostgreSQL
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Long idRoom;

    // La Clave Foránea: Muchas habitaciones pertenecen a un solo Hotel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", referencedColumnName = "id_hotel")
    private Hotels hotel;

    @Column(name = "tipo_habitacion", nullable = false, length = 100)
    private String roomType; // Ejemplo: "Estándar", "Suite Presidential", "Doble"

    @Column(name = "capacidad", nullable = false)
    private Integer capacity; // Número de personas permitidas (ej. 2, 4, 6)

    @Column(name = "precio_noche", nullable = false)
    private BigDecimal pricePerNight;

    @Column(name = "disponible")
    private Boolean isAvailable = true; // Para controlar si está ocupada o libre

    @Column(columnDefinition = "TEXT")
    private String descripcion; // Ejemplo: "Incluye aire acondicionado, Wi-Fi y balcón"
}