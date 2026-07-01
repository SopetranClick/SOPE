package com.sope.sopetran_click.controller.accomodation;


import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import com.sope.sopetran_click.dto.accommodation.RoomRequestDTO;
import com.sope.sopetran_click.dto.accommodation.RoomResponseDTO;
import com.sope.sopetran_click.service.accommodation.HotelsService;
import com.sope.sopetran_click.service.accommodation.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    private final HotelsService hotelsService;

    public RoomController(RoomService roomService, HotelsService hotelsService) {
        this.roomService = roomService;
        this.hotelsService = hotelsService;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> crearHabitacion(@Valid @RequestBody RoomRequestDTO dto) {
        RoomResponseDTO nuevaHabitacion = roomService.crearHabitacion(dto);
        return new ResponseEntity<>(nuevaHabitacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> listarTodas() {
        List<RoomResponseDTO> habitaciones = roomService.listarTodos();
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> buscarPorId(@PathVariable Long id) {
        RoomResponseDTO habitacion = roomService.buscarPorId(id);
        return ResponseEntity.ok(habitacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> actualizarHabitacion(@PathVariable Long id, @Valid @RequestBody RoomRequestDTO dto) {
        RoomResponseDTO habitacionActualizada = roomService.actualizarHabitacion(id, dto);
        return ResponseEntity.ok(habitacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        roomService.eliminarHabitacion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/hotels")
    public ResponseEntity<HotelResponseDTO> buscarPorIdhotel(@PathVariable Long id) {
        // Correcto: Coincide el tipo devuelto por el servicio (singular) con el tipo de la variable
        HotelResponseDTO hotel = hotelsService.buscarPorId(id);
        return ResponseEntity.ok(hotel);
    }


}
