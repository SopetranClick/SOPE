package com.sope.sopetran_click.controller.ecotourism;


import com.sope.sopetran_click.dto.ecotourism.IconicPlaceResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;
import com.sope.sopetran_click.service.ecotourism.Iconic_placeService;
import com.sope.sopetran_click.service.ecotourism.SiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;
    private final Iconic_placeService iconicPlaceService;

    public SiteController(SiteService siteService, Iconic_placeService iconicPlaceService) {
        this.siteService = siteService;
        this.iconicPlaceService = iconicPlaceService;
    }

    @GetMapping("/{id}/iconic-places")
    public ResponseEntity<List<IconicPlaceResponseDTO>> getIconicPlacesBySite(@PathVariable Long id) {
        return ResponseEntity.ok(iconicPlaceService.listarPorSite(id));
    }

    @GetMapping
    public ResponseEntity<List<SiteResponseDTO>> getAllSites() {
        List<SiteResponseDTO> sites = siteService.getAllSites();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> getSite(@PathVariable Long id) {
        SiteResponseDTO site = siteService.getSite(id);
        return ResponseEntity.ok(site);
    }

    @PostMapping
    public ResponseEntity<SiteResponseDTO> createSite(@Valid @RequestBody SiteRequestDTO dto) {
        SiteResponseDTO site = siteService.saveSite(dto);
        return new ResponseEntity<>(site, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> updateSite(@PathVariable Long id, @Valid @RequestBody SiteRequestDTO dto) {
        SiteResponseDTO site = siteService.updateSite(id, dto);
        return ResponseEntity.ok(site);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }

}
