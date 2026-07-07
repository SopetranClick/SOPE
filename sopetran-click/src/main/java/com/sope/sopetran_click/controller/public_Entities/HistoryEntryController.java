package com.sope.sopetran_click.controller.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.HistoryEntryRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.HistoryEntryResponseDTO;
import com.sope.sopetran_click.service.public_Entities.HistoryEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history-entries")
public class HistoryEntryController {

    private final HistoryEntryService historyEntryService;

    public HistoryEntryController(HistoryEntryService historyEntryService) {
        this.historyEntryService = historyEntryService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryEntryResponseDTO>> getAll() {
        return ResponseEntity.ok(historyEntryService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryEntryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(historyEntryService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistoryEntryResponseDTO> create(@Valid @RequestBody HistoryEntryRequestDTO dto) {
        return new ResponseEntity<>(historyEntryService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoryEntryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody HistoryEntryRequestDTO dto) {
        return ResponseEntity.ok(historyEntryService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historyEntryService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
