package com.sope.sopetran_click.controller.public_Entities;


import com.sope.sopetran_click.dto.public_Entities.MayoraltyRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.MayoraltyResponseDTO;
import com.sope.sopetran_click.service.public_Entities.MayoraltyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mayoralties")
public class MayoraltyController {
    private final MayoraltyService mayoraltyService;

    public MayoraltyController(MayoraltyService mayoraltyService) {
        this.mayoraltyService = mayoraltyService;
    }

    @GetMapping
    public ResponseEntity<List<MayoraltyResponseDTO>> getAllMayoralties() {
        List<MayoraltyResponseDTO> mayoralties = mayoraltyService.listarTodos();
        return ResponseEntity.ok(mayoralties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MayoraltyResponseDTO> getMayoralty(@PathVariable Long id) {
        MayoraltyResponseDTO mayoralty = mayoraltyService.buscarPorId(id);
        return ResponseEntity.ok(mayoralty);
    }

    @PostMapping
    public ResponseEntity<MayoraltyResponseDTO> createMayoralty(@Valid @RequestBody MayoraltyRequestDTO dto) {
        MayoraltyResponseDTO mayoralty = mayoraltyService.crear(dto);
        return new ResponseEntity<>(mayoralty, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MayoraltyResponseDTO> updateMayoralty(@PathVariable Long id, @Valid @RequestBody MayoraltyRequestDTO dto) {
        MayoraltyResponseDTO mayoralty = mayoraltyService.actualizar(id, dto);
        return ResponseEntity.ok(mayoralty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMayoralty(@PathVariable Long id) {
        mayoraltyService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
