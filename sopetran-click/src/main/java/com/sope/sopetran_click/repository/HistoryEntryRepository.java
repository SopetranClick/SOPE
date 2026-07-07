package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.public_Entities.HistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryEntryRepository extends JpaRepository<HistoryEntry, Long> {
    List<HistoryEntry> findAllByOrderByOrdenAsc();
}
