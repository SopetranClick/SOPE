package com.sope.sopetran_click.model.user;

import com.sope.sopetran_click.model.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// CORREGIDO: eliminado `import org.apache.catalina.User;` (import basura de autocompletado)
// También eliminado `import com.sope.sopetran_click.model.user.Payments;` redundante
// (Payments está en el mismo paquete, no necesita import)

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Long idBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_users", nullable = false)
    private Users user;

    // ID del objeto reservado (Hotel, Restaurante, etc.)
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // Tipo de categoría para saber qué tabla consultar
    // Ejemplo: "HOTEL", "RESTAURANT", "LOCAL", "TRANSPORT"
    @Column(name = "target_type", nullable = false, length = 50)
    private String targetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment", referencedColumnName = "id_payment")
    private Payments payment;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "description", length = 235)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, PAY, CANCELED
    }

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
}