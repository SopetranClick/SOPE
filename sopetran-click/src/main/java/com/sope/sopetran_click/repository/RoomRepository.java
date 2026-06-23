package com.sope.sopetran_click.repository;
import com.sope.sopetran_click.model.category.accommodation.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Encontrar habitaciones de un hotel en específico
    List<Room> findByHotelsIdHotel(Long idHotel);
}
