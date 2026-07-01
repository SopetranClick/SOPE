package com.sope.sopetran_click.model.user;


import com.sope.sopetran_click.model.Categorys;
import com.sope.sopetran_click.model.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private Long idPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categorys idCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_booking", referencedColumnName = "id_booking")
    private Booking idBooking;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;


    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private status state = status.PENDING;// 'turista' por defecto

    public enum status {
        PENDING,
        Pay,
        Canceled
    }

}
