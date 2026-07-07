package com.sope.sopetran_click.controller.user;

import com.sope.sopetran_click.dto.user.PqrsRequestDTO;
import com.sope.sopetran_click.dto.user.PqrsResponseDTO;
import com.sope.sopetran_click.service.user.PqrsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {

    private final PqrsService service;

    public PqrsController(PqrsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PqrsResponseDTO> crear(@Valid @RequestBody PqrsRequestDTO dto) {
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping // (para el admin; proteger cuando exista Spring Security)
    public ResponseEntity<List<PqrsResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
