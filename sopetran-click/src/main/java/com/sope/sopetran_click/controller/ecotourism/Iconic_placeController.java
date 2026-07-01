package com.sope.sopetran_click.controller.ecotourism;


import com.sope.sopetran_click.dto.ecotourism.IconicPlaceRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.IconicPlaceResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;
import com.sope.sopetran_click.service.ecotourism.Iconic_placeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iconic_places")
public class Iconic_placeController {

    private final Iconic_placeService iconicPlaceService;

    public Iconic_placeController(Iconic_placeService iconicPlaceService) {
        this.iconicPlaceService = iconicPlaceService;
    }

    @GetMapping
    public ResponseEntity<List<IconicPlaceResponseDTO>> listarTodos() {
        List<IconicPlaceResponseDTO> iconicPlaces = iconicPlaceService.getAllIconicPlaces();
        return ResponseEntity.ok(iconicPlaces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IconicPlaceResponseDTO> buscarPorId(@PathVariable Long id) {
        IconicPlaceResponseDTO iconicPlace = iconicPlaceService.getIconicPlace(id);
        return ResponseEntity.ok(iconicPlace);
    }

    @PostMapping
    public ResponseEntity<IconicPlaceResponseDTO> crearIconicPlace(@Valid @RequestBody IconicPlaceRequestDTO dto) {
        IconicPlaceResponseDTO iconicPlace = iconicPlaceService.createIconicPlace(dto);
        return new ResponseEntity<>(iconicPlace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IconicPlaceResponseDTO> actualizarIconicPlace(@PathVariable Long id, @Valid @RequestBody IconicPlaceRequestDTO dto) {
        IconicPlaceResponseDTO iconicPlace = iconicPlaceService.updateIconicPlace(id, dto);
        return ResponseEntity.ok(iconicPlace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIconicPlace(@PathVariable Long id) {
        iconicPlaceService.deleteIconicPlace(id);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/{id}/sites")
    public ResponseEntity <SiteResponseDTO> getSite(@PathVariable Long id) {
        SiteResponseDTO sites = iconicPlaceService.getSite(id);
        return ResponseEntity.ok(sites);
    }
}
