package com.sope.sopetran_click.model.category.trade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "local")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Long idLocal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trades", referencedColumnName = "id_trades")
    private Trades idTrades;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "type_local")
    private String type_local;

}
