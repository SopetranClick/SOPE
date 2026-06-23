package com.sope.sopetran_click.model.category.trade;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "productos") // Nombre de la tabla en tu PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProduct;

    // La Clave Foránea: Muchos productos pertenecen a un solo local comercial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", referencedColumnName = "id_local")
    private Local local; // Conexión directa a la clase de tu tienda/local

    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String name;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String description;

    @Column(name = "precio", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0; // Cantidad de unidades disponibles en la tienda

    @Column(name = "url_imagen", length = 255)
    private String imageUrl; // Foto del producto para el catálogo de Sopetrán Click

    @Column(name = "activo")
    private Boolean isActive = true; // Para pausar la publicación del producto si es necesario
}
