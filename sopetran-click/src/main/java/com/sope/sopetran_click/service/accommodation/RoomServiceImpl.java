package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import com.sope.sopetran_click.dto.accommodation.RoomRequestDTO;
import com.sope.sopetran_click.dto.accommodation.RoomResponseDTO;
import com.sope.sopetran_click.model.category.accommodation.Hotels;
import com.sope.sopetran_click.model.category.accommodation.Room;
import com.sope.sopetran_click.repository.HotelsRepository;
import com.sope.sopetran_click.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelsRepository hotelRepository;

    @Override
    public RoomResponseDTO buscarPorId(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro habitacion por esta ID"));
        return convertToResponseDTO(room);
    }

    @Override
    public HotelResponseDTO buscarPorIdhotel(Long idhotel) {
        Hotels hotel = hotelRepository.findById(idhotel)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        return convertToHotelResponseDTO(hotel);
    }


    @Override
    public List<RoomResponseDTO> listarTodos() {
        return roomRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .toList();
    }

    @Override
    public void eliminarHabitacion(Long id) {
        if(roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
        } else {
            throw new RuntimeException("Habitación no encontrada");
        }
    }

    @Override
    public RoomResponseDTO actualizarHabitacion(Long id, RoomRequestDTO room) {
        Hotels hotel = hotelRepository.findById(room.getIdHotel())
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        Room roomexiste = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        roomexiste.setCapacity(room.getCapacity());
        roomexiste.setPricePerNight(room.getPricePerNight());
        roomexiste.setRoomType(room.getRoomType());
        roomexiste.setDescripcion(room.getDescripcion());
        roomexiste.setIsAvailable(room.getIsAvailable());
        roomexiste.setHotel(hotel);
        Room roomGuardado = roomRepository.save(roomexiste);
        return convertToResponseDTO(roomGuardado);
    }

    @Override
    public RoomResponseDTO crearHabitacion(RoomRequestDTO room) {

        Hotels hotel = hotelRepository.findById(room.getIdHotel())
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        Room roomEntity = new Room();
        roomEntity.setHotel(hotel);
        roomEntity.setRoomType(room.getRoomType());
        roomEntity.setCapacity(room.getCapacity());
        roomEntity.setPricePerNight(room.getPricePerNight());
        roomEntity.setDescripcion(room.getDescripcion());

        Room roomGuardado = roomRepository.save(roomEntity);

        return convertToResponseDTO(roomGuardado);
    }

    private RoomResponseDTO convertToResponseDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setIdRoom(room.getIdRoom());
        dto.setCapacity(room.getCapacity());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setRoomType(room.getRoomType());
        dto.setDescripcion(room.getDescripcion());
        dto.setIsAvailable(room.getIsAvailable());
        dto.setHotelName(room.getHotel().getName());
        // Mapea aquí los demás campos que tengas en tu DTO
        if (dto.getHotelName() != null) {
            dto.setHotelName(room.getHotel().getName());
        }
        return dto;
    }

    // Este método traduce un objeto Hotels a HotelResponseDTO
    private HotelResponseDTO convertToHotelResponseDTO(Hotels hotel) {
        HotelResponseDTO dto = new HotelResponseDTO();
        dto.setIdHotel(hotel.getIdHotel()); // Ajusta según el nombre real del getter en tu entidad
        dto.setNombre(hotel.getName());
        dto.setDireccion(hotel.getAddress());
        dto.setPrecioNoche(hotel.getPrice());
        dto.setContacto(hotel.getContact());
        dto.setNombreAlojamiento(hotel.getIdAccommodation().getName());
        // Si tu DTO tiene el nombre de la categoría, lo seteamos aquí:
        if (hotel.getIdHotel() != null) {
            dto.setNombreAlojamiento(hotel.getIdAccommodation().getName());
        }
        return dto;
    }
}
