package com.sope.sopetran_click.controller.transport;


import com.sope.sopetran_click.dto.transport.BusesRequestDTO;
import com.sope.sopetran_click.dto.transport.BusesResponseDTO;
import com.sope.sopetran_click.service.transport.BuseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusesController {

    private final BuseService busService;

    public BusesController(BuseService busService) {
        this.busService = busService;
    }

    @GetMapping
    public ResponseEntity<List<BusesResponseDTO>> getAllBuses() {
        List<BusesResponseDTO> buses = busService.listarTodos();
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusesResponseDTO> getBusById(@PathVariable Long id) {
        BusesResponseDTO bus = busService.buscarPorId(id);
        return ResponseEntity.ok(bus);
    }

    @PostMapping
    public ResponseEntity<BusesResponseDTO> createBus(@Valid @RequestBody BusesRequestDTO dto) {
        BusesResponseDTO bus = busService.crear(dto);
        return new ResponseEntity<>(bus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusesResponseDTO> updateBus(@PathVariable Long id, @Valid @RequestBody BusesRequestDTO dto) {
        BusesResponseDTO bus = busService.actualizar(id, dto);
        return ResponseEntity.ok(bus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
