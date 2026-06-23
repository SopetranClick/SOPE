package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.public_Entities.mayoralty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MayoraltyRepository extends JpaRepository<mayoralty, Long> {
    // Para las dependencias de la alcaldía municipal
}
