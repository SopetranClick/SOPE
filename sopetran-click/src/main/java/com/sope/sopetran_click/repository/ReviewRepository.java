package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.user.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

    /**
     * Busca todas las reseñas realizadas por un usuario específico.
     * Spring Data JPA traduce 'findByIdUsers_IdUsers' a una consulta SQL basada
     * en el atributo idUsers de la clase Reviews y su atributo idUsers (el ID).
     */
    List<Reviews> findByIdUsers_IdUsers(Long idUsers);

    /**
     * Busca todas las reseñas asociadas a una categoría específica.
     */
    List<Reviews> findByIdCategory_IdCategory(Long idCategory);

    /**
     * Opcional: Si necesitas buscar por el item específico (negocio/hospedaje)
     */
    List<Reviews> findByIdItems(Long idItems);
}