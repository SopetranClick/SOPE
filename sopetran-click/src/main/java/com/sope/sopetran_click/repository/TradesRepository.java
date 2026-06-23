package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.trade.Trades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradesRepository extends JpaRepository<Trades, Long> {
}