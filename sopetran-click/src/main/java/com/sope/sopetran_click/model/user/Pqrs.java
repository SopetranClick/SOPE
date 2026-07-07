package com.sope.sopetran_click.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pqrs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pqrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPqrs;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 30)
    private String telefono;

    @Column(nullable = false, length = 150)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false, length = 20)
    private String estado;

    private LocalDateTime creadoEn;

    @PrePersist
    void prePersist() {
        this.creadoEn = LocalDateTime.now();
        if (this.estado == null) this.estado = "NUEVA";
    }
}
