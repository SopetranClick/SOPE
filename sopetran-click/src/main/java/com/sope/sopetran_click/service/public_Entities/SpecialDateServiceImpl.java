package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.SpecialDateRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.SpecialDateResponseDTO;
import com.sope.sopetran_click.model.category.public_Entities.Public_Entitie;
import com.sope.sopetran_click.model.category.public_Entities.SpecialDate;
import com.sope.sopetran_click.repository.PublicEntitieRepository;
import com.sope.sopetran_click.repository.SpecialDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialDateServiceImpl implements SpecialDateService {

    private final SpecialDateRepository specialDateRepository;
    private final PublicEntitieRepository publicEntitieRepository;

    @Override
    @Transactional
    public SpecialDateResponseDTO crear(SpecialDateRequestDTO dto) {
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        SpecialDate entity = new SpecialDate();
        mapearDtoAEntidad(dto, entity, publicEntitie);
        return convertToResponseDTO(specialDateRepository.save(entity));
    }

    @Override
    @Transactional
    public SpecialDateResponseDTO actualizar(Long id, SpecialDateRequestDTO dto) {
        SpecialDate entity = specialDateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fecha especial no encontrada"));
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        mapearDtoAEntidad(dto, entity, publicEntitie);
        return convertToResponseDTO(specialDateRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialDateResponseDTO buscarPorId(Long id) {
        return specialDateRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Fecha especial no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialDateResponseDTO> listarTodos() {
        return specialDateRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!specialDateRepository.existsById(id)) {
            throw new RuntimeException("Fecha especial no encontrada");
        }
        specialDateRepository.deleteById(id);
    }

    private void mapearDtoAEntidad(SpecialDateRequestDTO dto, SpecialDate entity, Public_Entitie publicEntitie) {
        entity.setIdPublicEntitie(publicEntitie);
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setDia(dto.getDia());
        entity.setMes(dto.getMes());
    }

    private SpecialDateResponseDTO convertToResponseDTO(SpecialDate entity) {
        SpecialDateResponseDTO dto = new SpecialDateResponseDTO();
        dto.setIdSpecialDate(entity.getIdSpecialDate());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDia(entity.getDia());
        dto.setMes(entity.getMes());
        if (entity.getIdPublicEntitie() != null) {
            dto.setIdPublicEntitie(entity.getIdPublicEntitie().getIdPublicEntitie());
        }
        return dto;
    }
}
