package com.sope.sopetran_click.controller.accomodation;


import com.sope.sopetran_click.dto.accommodation.EstateRequestDTO;
import com.sope.sopetran_click.dto.accommodation.EstateResponseDTO;
import com.sope.sopetran_click.dto.accommodation.HotelRequestDTO;
import com.sope.sopetran_click.dto.accommodation.HotelResponseDTO;
import com.sope.sopetran_click.service.accommodation.EstateService;
import com.sope.sopetran_click.service.accommodation.HotelsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelsController {
    private final HotelsService hotelService;

    public HotelsController(HotelsService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> listarTodos() {
        List<HotelResponseDTO> hoteles = hotelService.listarTodos();
        return ResponseEntity.ok(hoteles);
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> crearHotel(@Valid @RequestBody HotelRequestDTO dto) {
        HotelResponseDTO nuevoHotel = hotelService.crearHotel(dto);
        return new ResponseEntity<>(nuevoHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> buscarPorId(@PathVariable Long id) {
        HotelResponseDTO hotel = hotelService.buscarPorId(id);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> actualizarHotel(@PathVariable Long id, @Valid @RequestBody HotelRequestDTO dto) {
        HotelResponseDTO hotelActualizado = hotelService.actualizarHotel(id, dto);
        return ResponseEntity.ok(hotelActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHotel(@PathVariable Long id) {
        hotelService.eliminarHotel(id);
        return ResponseEntity.noContent().build();
    }

}
