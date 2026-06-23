package com.sope.sopetran_click.repository;


import com.sope.sopetran_click.model.category.ecotourism.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
}
