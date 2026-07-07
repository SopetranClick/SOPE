package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.transport.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByTipoVehiculo(String tipoVehiculo);
}
