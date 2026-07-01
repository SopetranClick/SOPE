package com.sope.sopetran_click.controller.trade;


import com.sope.sopetran_click.dto.trade.LocalRequestDTO;
import com.sope.sopetran_click.dto.trade.LocalResponseDTO;
import com.sope.sopetran_click.service.trade.LocalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;

    }

    @GetMapping
    public ResponseEntity<List<LocalResponseDTO>> getAllLocals() {
        List<LocalResponseDTO> locals = localService.listarTodos();
        return ResponseEntity.ok(locals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> getLocalById(@PathVariable Long id) {
        LocalResponseDTO local = localService.buscarPorId(id);
        return ResponseEntity.ok(local);
    }

    @PostMapping
    public ResponseEntity<LocalResponseDTO> createLocal(@Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO local = localService.crearLocal(dto);
        return new ResponseEntity<>(local, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> updateLocal(@PathVariable Long id, @Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO local = localService.actualizarLocal(id, dto);
        return ResponseEntity.ok(local);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        localService.eliminarLocal(id);
        return ResponseEntity.noContent().build();
    }
}
