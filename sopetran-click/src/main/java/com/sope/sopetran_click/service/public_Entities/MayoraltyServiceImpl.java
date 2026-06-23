package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.MayoraltyRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.MayoraltyResponseDTO;
import com.sope.sopetran_click.model.category.public_Entities.mayoralty;
import com.sope.sopetran_click.model.category.public_Entities.Public_Entitie;
import com.sope.sopetran_click.repository.MayoraltyRepository;
import com.sope.sopetran_click.repository.PublicEntitieRepository;
import com.sope.sopetran_click.service.public_Entities.MayoraltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MayoraltyServiceImpl implements MayoraltyService {

    private final MayoraltyRepository mayoraltyRepository;
    private final PublicEntitieRepository publicEntitieRepository;

    @Override
    @Transactional
    public MayoraltyResponseDTO crear(MayoraltyRequestDTO dto) {
        // Validar y obtener relación
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new IllegalArgumentException("Entidad Pública no encontrada"));

        // Crear y mapear campos
        mayoralty mayoralty1 = new mayoralty();
        mayoralty1.setIdPublicEntitie(publicEntitie);
        mayoralty1.setDescription(dto.getDependencia());
        mayoralty1.setContact(dto.getContacto());

        mayoralty saved = mayoraltyRepository.save(mayoralty1);

        // Retornar respuesta mapeada
        return new MayoraltyResponseDTO(
                saved.getIdMayoralty(),
                saved.getDescription(),
                saved.getContact(),
                saved.getIdPublicEntitie().getDescription()
        );
    }

    @Override
    @Transactional
    public MayoraltyResponseDTO actualizar(Long id, MayoraltyRequestDTO dto) {
        mayoralty mayoralty1 = mayoraltyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mayoralty no encontrada con ID: " + id));

        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new IllegalArgumentException("Entidad Pública no encontrada"));

        // Mapeo explícito de todos los campos
        mayoralty1.setIdPublicEntitie(publicEntitie);
        mayoralty1.setDescription(dto.getDependencia());
        mayoralty1.setContact(dto.getContacto());

        mayoralty updated = mayoraltyRepository.save(mayoralty1);

        return new MayoraltyResponseDTO(
                updated.getIdMayoralty(),
                updated.getDescription(),
                updated.getContact(),
                updated.getIdPublicEntitie().getDescription()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MayoraltyResponseDTO buscarPorId(Long id) {
        return mayoraltyRepository.findById(id)
                .map(this::mapearAResponse)
                .orElseThrow(() -> new IllegalArgumentException("Mayoralty no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MayoraltyResponseDTO> listarTodos() {
        return mayoraltyRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!mayoraltyRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar, Mayoralty no encontrada con ID: " + id);
        }
        mayoraltyRepository.deleteById(id);
    }

    private MayoraltyResponseDTO mapearAResponse(mayoralty m) {
        return new MayoraltyResponseDTO(
                m.getIdMayoralty(),
                m.getDescription(),
                m.getContact(),
                m.getIdPublicEntitie() != null ? m.getIdPublicEntitie().getDescription() : null
        );
    }
}