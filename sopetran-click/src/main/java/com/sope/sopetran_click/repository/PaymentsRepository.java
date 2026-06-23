package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.user.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    List<Payments> findAll();
    List<Payments> findByUserId(Long userId);
}
