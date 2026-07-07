package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.HotelRequestDTO;
import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import com.sope.sopetran_click.model.category.accommodation.Accommodations;
import com.sope.sopetran_click.model.category.accommodation.Hotels;
import com.sope.sopetran_click.repository.AccommodationsRepository;
import com.sope.sopetran_click.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelsServiceImpl implements HotelsService {

    @Autowired
    private HotelsRepository hotelsRepository;

    @Autowired
    private AccommodationsRepository accommodationsRepository;

    @Override
    @Transactional
    public HotelResponseDTO crearHotel(HotelRequestDTO dto) {
        // 1. Buscar el alojamiento padre en la BD
        Accommodations alojamiento = accommodationsRepository.findById(dto.getIdAccommodation())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        // 2. Mapear DTO a la Entidad Hotels
        Hotels hotel = new Hotels();
        hotel.setName(dto.getName());
        hotel.setAddress(dto.getAddress());
        hotel.setPrice(dto.getPricePerNight());
        hotel.setContact(dto.getContact());
        hotel.setIdAccommodation(alojamiento); // Asignación de la FK como objeto completo

        // 3. Guardar en base de datos
        Hotels hotelGuardado = hotelsRepository.save(hotel);

        // 4. Retornar DTO de Respuesta
        return convertToResponseDTO(hotelGuardado);
    }

    @Override
    @Transactional
    public HotelResponseDTO actualizarHotel(Long id, HotelRequestDTO dto) {
        Hotels hotelExistente = hotelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado con ID: " + id));

        Accommodations alojamiento = accommodationsRepository.findById(dto.getIdAccommodation())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        hotelExistente.setName(dto.getName());
        hotelExistente.setAddress(dto.getAddress());
        hotelExistente.setPrice(dto.getPricePerNight());
        hotelExistente.setContact(dto.getContact());
        hotelExistente.setIdAccommodation(alojamiento);

        Hotels hotelActualizado = hotelsRepository.save(hotelExistente);
        return convertToResponseDTO(hotelActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponseDTO buscarPorId(Long id) {
        Hotels hotel = hotelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado "));
        return convertToResponseDTO(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelResponseDTO> listarTodos() {
        return hotelsRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarHotel(Long id) {
        if (!hotelsRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Hotel no encontrado por ID ");
        }
        hotelsRepository.deleteById(id);
    }

    // Método auxiliar de mapeo (Entity -> DTO de respuesta)
    private HotelResponseDTO convertToResponseDTO(Hotels hotel) {
        HotelResponseDTO response = new HotelResponseDTO();
        response.setIdHotel(hotel.getIdHotel());
        response.setName(hotel.getName());
        response.setAddress(hotel.getAddress());
        response.setPricePerNight(hotel.getPrice());
        response.setContact(hotel.getContact());
        response.setDescription(hotel.getDescription());

        // Obtenemos de forma segura la información de la categoría padre
        if (hotel.getIdAccommodation() != null) {
            response.setAccommodationType(hotel.getIdAccommodation().getName());
        }

        // Mapear imágenes desde la relación @OneToMany
        if (hotel.getImagenes() != null && !hotel.getImagenes().isEmpty()) {
            response.setCoverUrl(
                    hotel.getImagenes().stream()
                            .filter(i -> i.getOrden() == 0)
                            .map(i -> i.getUrl())
                            .findFirst()
                            .orElse("/img/placeholder-hotel.jpg")
            );
            response.setGallery(
                    hotel.getImagenes().stream()
                            .filter(i -> i.getOrden() > 0)
                            .map(i -> i.getUrl())
                            .collect(Collectors.toList())
            );
        } else {
            response.setCoverUrl("/img/placeholder-hotel.jpg");
            response.setGallery(List.of());
        }
        return response;
    }
}
