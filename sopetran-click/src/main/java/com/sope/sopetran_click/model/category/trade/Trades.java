package com.sope.sopetran_click.model.category.trade;

import com.sope.sopetran_click.model.Categorys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "trades")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Trades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trades")
    private Long idTrades;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorys", referencedColumnName = "id_categorys")
    private Categorys idCategorys;

    
    @Column(name = "description")
    private String description;

}
