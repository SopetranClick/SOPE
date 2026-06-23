package com.sope.sopetran_click.repository;


import com.sope.sopetran_click.model.category.public_Entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // Traer las noticias más recientes ordenadas de forma descendente
    List<News> findAllByOrderByFechaPublicacionDesc();
}
