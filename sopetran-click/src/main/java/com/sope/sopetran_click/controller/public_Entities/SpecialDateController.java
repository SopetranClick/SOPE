package com.sope.sopetran_click.controller.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.SpecialDateRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.SpecialDateResponseDTO;
import com.sope.sopetran_click.service.public_Entities.SpecialDateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/special-dates")
public class SpecialDateController {

    private final SpecialDateService specialDateService;

    public SpecialDateController(SpecialDateService specialDateService) {
        this.specialDateService = specialDateService;
    }

    @GetMapping
    public ResponseEntity<List<SpecialDateResponseDTO>> getAll() {
        return ResponseEntity.ok(specialDateService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialDateResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(specialDateService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SpecialDateResponseDTO> create(@Valid @RequestBody SpecialDateRequestDTO dto) {
        return new ResponseEntity<>(specialDateService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialDateResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SpecialDateRequestDTO dto) {
        return ResponseEntity.ok(specialDateService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialDateService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
