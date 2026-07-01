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
        estate.setName(dto.getNombre());
        estate.setAddress(dto.getUbicacion());
        estate.setPrice(dto.getPrecioPorNoche());
        estate.setContact(dto.getContacto());
        estate.setDescription(dto.getDescripcion());
        estate.setIdAccommodation(alojamiento);
        estate.setTypeEstate(dto.getTipoFinca());
        Estate estateGuardado = estateRepository.save(estate);

        return convertToResponseDTO(estateGuardado);
    }



    @Override
    public EstateResponseDTO actualizarFinca(Long id, EstateRequestDTO dto) {
        Estate estateExistente = estateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrado"));

        Accommodations alojamiento = accommodationsRepository.findById(dto.getIdAccommodation())
                .orElseThrow(() -> new RuntimeException("Categoría de alojamiento base no encontrada."));

        estateExistente.setName(dto.getNombre());
        estateExistente.setAddress(dto.getUbicacion());
        estateExistente.setDescription(dto.getDescripcion());
        estateExistente.setPrice(dto.getPrecioPorNoche());
        estateExistente.setContact(dto.getContacto());

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
        dto.setNombre(estate.getName());
        dto.setUbicacion(estate.getAddress());
        dto.setPrecioPorNoche(estate.getPrice());
        dto.setContacto(estate.getContact());
        // Mapea aquí los demás campos que tengas en tu DTO
        return dto;
    }
}
