package com.sope.sopetran_click.model.category.trade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "platos") // Nombre exacto de la tabla en tu PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato")
    private Long idDish;

    // La Clave Foránea: Muchos platos pertenecen a un solo restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id_restaurante")
    private Restaurant restaurant; // Reutiliza tu clase modelo de restaurante aquí

    @Column(name = "nombre_plato", nullable = false, length = 150)
    private String name;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String description; // Para detallar los ingredientes del plato

    @Column(name = "precio", nullable = false)
    private BigDecimal price; // Uso de BigDecimal para evitar errores de redondeo en caja

    @Column(name = "disponible")
    private Boolean isAvailable = true; // Permite al comerciante "desactivar" el plato si se agota

    @Column(name = "url_imagen", length = 255)
    private String imageUrl; // Ruta o link de la foto del plato para mostrarla en el menú de la app
}
