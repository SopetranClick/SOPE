package com.sope.sopetran_click.controller.transport;

import com.sope.sopetran_click.dto.transport.DriverRequestDTO;
import com.sope.sopetran_click.dto.transport.DriverResponseDTO;
import com.sope.sopetran_click.service.transport.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.buscarPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(driverService.listarPorTipoVehiculo(tipo));
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO dto) {
        DriverResponseDTO driver = driverService.crear(dto);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequestDTO dto) {
        return ResponseEntity.ok(driverService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
