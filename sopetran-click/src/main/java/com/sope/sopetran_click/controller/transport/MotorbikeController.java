package com.sope.sopetran_click.controller.transport;


import com.sope.sopetran_click.dto.transport.MotorbikeRequestDTO;
import com.sope.sopetran_click.dto.transport.MotorbikeResponseDTO;
import com.sope.sopetran_click.service.transport.MotorbikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motorbike")
public class MotorbikeController {

    private final MotorbikeService motorbikeService;

    public MotorbikeController(MotorbikeService motorbikeService) {
        this.motorbikeService = motorbikeService;
    }

    @GetMapping
    public ResponseEntity<List<MotorbikeResponseDTO>> getAllMotorbikes() {
        List<MotorbikeResponseDTO> motorbikes = motorbikeService.listarTodos();
        return ResponseEntity.ok(motorbikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotorbikeResponseDTO> getMotorbikeById(@PathVariable Long id) {
        MotorbikeResponseDTO motorbike = motorbikeService.buscarPorId(id);
        return ResponseEntity.ok(motorbike);
    }

    @PostMapping
    public ResponseEntity<MotorbikeResponseDTO> createMotorbike(@Valid @RequestBody MotorbikeRequestDTO dto) {
        MotorbikeResponseDTO motorbike = motorbikeService.crear(dto);
        return new ResponseEntity<>(motorbike, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorbikeResponseDTO> updateMotorbike(@PathVariable Long id, @Valid @RequestBody MotorbikeRequestDTO dto) {
        MotorbikeResponseDTO motorbike = motorbikeService.actualizar(id, dto);
        return ResponseEntity.ok(motorbike);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorbike(@PathVariable Long id) {
        motorbikeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
