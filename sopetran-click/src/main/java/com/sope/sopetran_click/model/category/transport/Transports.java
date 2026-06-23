package com.sope.sopetran_click.model.category.transport;


import com.sope.sopetran_click.model.Categorys;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "transports")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Transports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transports")
    private Long idTransports;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Categorys idCategory;

    @Column(name = "description")
    private String description;

}
