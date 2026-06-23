package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorysRepository extends JpaRepository<Categorys, Long> {

    // Buscar categoría por su nombre exacto (ej. "Alojamiento", "Comercio")
    Categorys findByNameCategory(String nameCategory);
}
