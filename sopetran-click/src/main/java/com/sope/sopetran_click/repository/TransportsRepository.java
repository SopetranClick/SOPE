package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.transport.Transports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportsRepository extends JpaRepository<Transports, Long> {
}
