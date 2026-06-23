package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.public_Entities.Public_Entitie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicEntitieRepository extends JpaRepository<Public_Entitie, Long> {
}
