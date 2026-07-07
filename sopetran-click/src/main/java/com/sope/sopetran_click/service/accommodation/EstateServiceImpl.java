package com.sope.sopetran_click.service.accommodation;

import com.sope.sopetran_click.dto.accommodation.EstateRequestDTO;
import com.sope.sopetran_click.dto.accommodation.EstateResponseDTO;
import com.sope.sopetran_click.model.category.accommodation.Estate;
import com.sope.sopetran_click.repository.AccommodationsRepository;
import com.sope.sopetran_click.model.category.accommodation.Accommodations;
import com.sope.sopetran_click.repository.EstateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstateServiceImpl implements EstateService{

    @Autowired
    private EstateRepository estateRepository;

    @Autowired
    private AccommodationsRepository accommodationsRepository;

    @Override
    @Transactional
    public EstateResponseDTO crearFinca(EstateRequestDTO dto) {
        Accommodations alojamiento= accommodationsRepository.findById(dto.getIdAccommodation())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        Estate  estate = new Estate();
        estate.setName(dto.getName());
        estate.setAddress(dto.getLocation());
        estate.setPrice(dto.getPricePerNight());
        estate.setContact(dto.getContact());
        estate.setDescription(dto.getDescription());
        estate.setIdAccommodation(alojamiento);
        estate.setTypeEstate(dto.getFarmType());
        Estate estateGuardado = estateRepository.save(estate);

        return convertToResponseDTO(estateGuardado);
    }



    @Override
    public EstateResponseDTO actualizarFinca(Long id, EstateRequestDTO dto) {
        Estate estateExistente = estateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrado"));

        Accommodations alojamiento = accommodationsRepository.findById(dto.getIdAccommodation())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        estateExistente.setName(dto.getName());
        estateExistente.setAddress(dto.getLocation());
        estateExistente.setDescription(dto.getDescription());
        estateExistente.setPrice(dto.getPricePerNight());
        estateExistente.setContact(dto.getContact());
        estateExistente.setTypeEstate(dto.getFarmType());

        Estate estateActualizado = estateRepository.save(estateExistente);


        return convertToResponseDTO(estateActualizado);
    }

    @Override
    public EstateResponseDTO buscarPorId(Long id) {
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada "));

        return convertToResponseDTO(estate);
    }

    @Override
    public List<EstateResponseDTO> listarTodos() {

        return estateRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarFinca(Long id) {
        if (!estateRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Finca no encontrada ");
        }
        estateRepository.deleteById(id);
    }

    private EstateResponseDTO convertToResponseDTO(Estate estate) {
        EstateResponseDTO dto = new EstateResponseDTO();
        dto.setIdEstate(estate.getIdEstate());
        dto.setName(estate.getName());
        dto.setLocation(estate.getAddress());
        dto.setPricePerNight(estate.getPrice());
        dto.setContact(estate.getContact());
        dto.setDescription(estate.getDescription());
        dto.setFarmType(estate.getTypeEstate());

        // Mapear imágenes desde la relación @OneToMany
        if (estate.getImagenes() != null && !estate.getImagenes().isEmpty()) {
            dto.setCoverUrl(
                    estate.getImagenes().stream()
                            .filter(i -> i.getOrden() == 0)
                            .map(i -> i.getUrl())
                            .findFirst()
                            .orElse("/img/placeholder-finca.jpg")
            );
            dto.setGallery(
                    estate.getImagenes().stream()
                            .filter(i -> i.getOrden() > 0)
                            .map(i -> i.getUrl())
                            .collect(Collectors.toList())
            );
        } else {
            dto.setCoverUrl("/img/placeholder-finca.jpg");
            dto.setGallery(List.of());
        }

        return dto;
    }
}
