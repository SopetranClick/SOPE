package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.category.trade.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Obtener catálogo de productos de un almacén o local específico
    List<Product> findByLocalIdLocal(Long idLocal);

    // Traer productos con inventario activo
    List<Product> findByStockGreaterThan(Integer stock);
}
