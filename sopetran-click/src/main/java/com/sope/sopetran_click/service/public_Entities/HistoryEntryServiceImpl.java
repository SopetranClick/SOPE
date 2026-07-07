package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.HistoryEntryRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.HistoryEntryResponseDTO;
import com.sope.sopetran_click.model.category.public_Entities.HistoryEntry;
import com.sope.sopetran_click.model.category.public_Entities.Public_Entitie;
import com.sope.sopetran_click.repository.HistoryEntryRepository;
import com.sope.sopetran_click.repository.PublicEntitieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryEntryServiceImpl implements HistoryEntryService {

    private final HistoryEntryRepository historyEntryRepository;
    private final PublicEntitieRepository publicEntitieRepository;

    @Override
    @Transactional
    public HistoryEntryResponseDTO crear(HistoryEntryRequestDTO dto) {
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        HistoryEntry entity = new HistoryEntry();
        mapearDtoAEntidad(dto, entity, publicEntitie);
        return convertToResponseDTO(historyEntryRepository.save(entity));
    }

    @Override
    @Transactional
    public HistoryEntryResponseDTO actualizar(Long id, HistoryEntryRequestDTO dto) {
        HistoryEntry entity = historyEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada de historia no encontrada"));
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        mapearDtoAEntidad(dto, entity, publicEntitie);
        return convertToResponseDTO(historyEntryRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryEntryResponseDTO buscarPorId(Long id) {
        return historyEntryRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Entrada de historia no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryEntryResponseDTO> listarTodos() {
        return historyEntryRepository.findAllByOrderByOrdenAsc().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!historyEntryRepository.existsById(id)) {
            throw new RuntimeException("Entrada de historia no encontrada");
        }
        historyEntryRepository.deleteById(id);
    }

    private void mapearDtoAEntidad(HistoryEntryRequestDTO dto, HistoryEntry entity, Public_Entitie publicEntitie) {
        entity.setIdPublicEntitie(publicEntitie);
        entity.setEra(dto.getEra());
        entity.setTitulo(dto.getTitulo());
        entity.setTexto(dto.getTexto());
        entity.setNumero(dto.getNumero());
        entity.setOrden(dto.getOrden() != null ? dto.getOrden() : 0);
        entity.setMain(dto.getMain() != null ? dto.getMain() : false);
    }

    private HistoryEntryResponseDTO convertToResponseDTO(HistoryEntry entity) {
        HistoryEntryResponseDTO dto = new HistoryEntryResponseDTO();
        dto.setIdHistoryEntry(entity.getIdHistoryEntry());
        dto.setEra(entity.getEra());
        dto.setTitulo(entity.getTitulo());
        dto.setTexto(entity.getTexto());
        dto.setNumero(entity.getNumero());
        dto.setOrden(entity.getOrden());
        dto.setMain(entity.getMain());
        if (entity.getIdPublicEntitie() != null) {
            dto.setIdPublicEntitie(entity.getIdPublicEntitie().getIdPublicEntitie());
        }
        return dto;
    }
}
