package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.user.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
    List<Pqrs> findByEstadoOrderByCreadoEnDesc(String estado);
}
