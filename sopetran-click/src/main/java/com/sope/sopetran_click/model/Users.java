package com.sope.sopetran_click.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_users")
    private Long idUsers;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email",nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(length = 20)
    private String telefono;

    // Aquí mapeamos el ENUM de Postgres como String para evitar conflictos
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private RolUsers role = RolUsers.Tourist; // 'turista' por defecto

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // =========================================================================
    // ENUM INTERNO: Define los tres tipos de usuario para el control de accesos
    // =========================================================================
    public enum RolUsers {
        Tourist,
        Businessman,
        admin
    }
}





