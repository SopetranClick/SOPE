package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.accommodation.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {

    // Buscar fincas por tipo (ej. "recreativa", "casa campestre")
    List<Estate> findByTypeEstate(String tipoFinca);
}
