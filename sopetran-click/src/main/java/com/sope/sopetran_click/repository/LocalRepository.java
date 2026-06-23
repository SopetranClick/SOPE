package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.trade.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
}
