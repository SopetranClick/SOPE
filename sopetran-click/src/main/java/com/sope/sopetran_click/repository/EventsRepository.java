package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.public_Entities.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
}