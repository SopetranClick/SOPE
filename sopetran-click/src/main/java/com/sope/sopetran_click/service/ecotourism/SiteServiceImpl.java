package com.sope.sopetran_click.service.ecotourism;

import com.sope.sopetran_click.dto.ecotourism.SiteRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;
import com.sope.sopetran_click.model.category.ecotourism.Ecotourism;
import com.sope.sopetran_click.model.category.ecotourism.Site;
import com.sope.sopetran_click.repository.EcoturismRepository;
import com.sope.sopetran_click.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Autowired
    private EcoturismRepository ecoturismRepository;

    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public SiteResponseDTO getSite(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sitio no encontrado con ID: " + id));
        return convertToSiteResponseDTO(site);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteResponseDTO> getAllSites() {
        return siteRepository.findAll().stream()
                .map(this::convertToSiteResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SiteResponseDTO saveSite(SiteRequestDTO site) {
        Ecotourism ecoturism = ecoturismRepository.findById(site.getIdEcoturismo())
                .orElseThrow(() -> new RuntimeException("Ecoturismo no encontrado"));

        Site newSite = new Site();
        newSite.setIdEcotourism(ecoturism); // Asegúrate de que el nombre del setter coincida con tu entidad
        newSite.setName(site.getNombreVereda());
        newSite.setDescription(site.getDescripcion());
        newSite.setTags(site.getTags());

        Site savedSite = siteRepository.save(newSite);
        return convertToSiteResponseDTO(savedSite);
    }

    @Override
    @Transactional
    public SiteResponseDTO updateSite(Long id, SiteRequestDTO dto) {
        Site existingSite = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sitio no encontrado"));

        existingSite.setName(dto.getNombreVereda());
        existingSite.setDescription(dto.getDescripcion());
        existingSite.setTags(dto.getTags());

        Site updatedSite = siteRepository.save(existingSite);
        return convertToSiteResponseDTO(updatedSite);
    }

    @Override
    @Transactional
    public void deleteSite(Long id) {
        if (!siteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Sitio no encontrado");
        }
        siteRepository.deleteById(id);
    }

    // Método auxiliar de conversión (Mapper)
    private SiteResponseDTO convertToSiteResponseDTO(Site site) {
        SiteResponseDTO dto = new SiteResponseDTO();
        dto.setIdVereda(site.getIdSite()); // Asegúrate que sea el nombre correcto del ID
        dto.setNombreVereda(site.getName());
        dto.setDescripcion(site.getDescription());
        dto.setTags(site.getTags());
        dto.setCoverUrl(site.getCoverUrl());
        if (site.getImagenes() != null && !site.getImagenes().isEmpty()) {
            dto.setGallery(
                    site.getImagenes().stream()
                            .filter(i -> i.getOrden() > 0)
                            .map(i -> i.getUrl())
                            .collect(Collectors.toList())
            );
        } else {
            dto.setGallery(Collections.emptyList());
        }
        return dto;
    }
}