package com.sope.sopetran_click.controller.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.EventsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.EventsResponseDTO;
import com.sope.sopetran_click.service.public_Entities.EventsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventsService eventService;

    public EventController(EventsService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventsResponseDTO>> getAllEvents() {
        List<EventsResponseDTO> events = eventService.listarTodos();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventsResponseDTO> getEvent(@PathVariable Long id) {
        EventsResponseDTO event = eventService.buscarPorId(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventsResponseDTO> createEvent(@Valid @RequestBody EventsRequestDTO dto) {
        EventsResponseDTO event = eventService.crear(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventsResponseDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventsRequestDTO dto) {
        EventsResponseDTO event = eventService.actualizar(id, dto);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
