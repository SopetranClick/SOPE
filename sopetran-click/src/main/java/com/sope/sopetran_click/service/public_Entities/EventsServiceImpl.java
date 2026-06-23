package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.EventsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.EventsResponseDTO;
import com.sope.sopetran_click.model.category.public_Entities.Events;
import com.sope.sopetran_click.repository.EventsRepository;
import com.sope.sopetran_click.repository.PublicEntitieRepository;
import com.sope.sopetran_click.service.public_Entities.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final PublicEntitieRepository publicEntitieRepository;

    @Override
    @Transactional
    public EventsResponseDTO crear(EventsRequestDTO dto) {
        var entity = new Events();
        var publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        entity.setIdPublicEntitie(publicEntitie);
        entity.setDescription(dto.getNombreEvento());
        entity.setDate(dto.getFechaEvento());
        entity.setVenues(dto.getLugar());

        return mapToResponse(eventsRepository.save(entity));
    }

    @Override
    @Transactional
    public EventsResponseDTO actualizar(Long id, EventsRequestDTO dto) {
        var event = eventsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        var publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new RuntimeException("Entidad pública no encontrada"));

        event.setIdPublicEntitie(publicEntitie);
        event.setDescription(dto.getNombreEvento());
        event.setDate(dto.getFechaEvento());
        event.setVenues(dto.getLugar());

        return mapToResponse(eventsRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public EventsResponseDTO buscarPorId(Long id) {
        return eventsRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventsResponseDTO> listarTodos() {
        return eventsRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (eventsRepository.existsById(id)) {
            eventsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Evento no encontrado");
        }
    }

    private EventsResponseDTO mapToResponse(Events event) {
        return new EventsResponseDTO(
                event.getIdEvent(),
                event.getDescription(),
                event.getDate(),
                event.getVenues(),
                event.getIdPublicEntitie().getDescription() // Ajusta según el campo real en tu modelo Public_Entitie
        );
    }
}