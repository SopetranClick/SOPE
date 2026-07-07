package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.public_Entities.SpecialDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialDateRepository extends JpaRepository<SpecialDate, Long> {
    List<SpecialDate> findByIdPublicEntitie_IdPublicEntitie(Long idPublicEntitie);
}
