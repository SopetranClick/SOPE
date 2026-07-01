package com.sope.sopetran_click.service.ecotourism;

import com.sope.sopetran_click.dto.accommodation.RoomResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.IconicPlaceRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.IconicPlaceResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;
import com.sope.sopetran_click.model.category.accommodation.Room;
import com.sope.sopetran_click.model.category.ecotourism.Iconic_place;
import com.sope.sopetran_click.model.category.ecotourism.Site;
import com.sope.sopetran_click.repository.IconicPlaceRepository;
import com.sope.sopetran_click.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class Iconic_placeSerivceImpl implements Iconic_placeService {

    @Autowired
    private IconicPlaceRepository iconicPlaceRepository;

    @Autowired
    private SiteRepository siteRepository;
    @Override
    public IconicPlaceResponseDTO getIconicPlace(Long id) {

        Iconic_place iconicPlace = iconicPlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lugar icónico no encontrado"));
        return convertToResponseDTO(iconicPlace);
    }



    @Override
    public IconicPlaceResponseDTO createIconicPlace(IconicPlaceRequestDTO iconicPlace) {
        Site site = siteRepository.findById(iconicPlace.getIdLugar())
            .orElseThrow(() -> new RuntimeException("Site no encontrado"));

        Iconic_place iconicPlaceEntity = new Iconic_place();
        iconicPlaceEntity.setIdsite(site);
        iconicPlaceEntity.setName(iconicPlace.getNombreLugar());
        iconicPlaceEntity.setDescription(iconicPlace.getIndicaciones());

        Iconic_place iconicGuardado = iconicPlaceRepository.save(iconicPlaceEntity);

        return convertToResponseDTO(iconicGuardado);
    }

    @Override
    public IconicPlaceResponseDTO updateIconicPlace(Long id, IconicPlaceRequestDTO iconicPlace) {
        Site site = siteRepository.findById(iconicPlace.getIdLugar())
                .orElseThrow(() -> new RuntimeException("Site no encontrado"));

        Iconic_place iconicexisted = iconicPlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lugar icónico no encontrado"));
        iconicexisted.setIdsite(site);
        iconicexisted.setName(iconicPlace.getNombreLugar());
        iconicexisted.setDescription(iconicPlace.getIndicaciones());

        Iconic_place iconicUpdate = iconicPlaceRepository.save(iconicexisted);
        return convertToResponseDTO(iconicUpdate);
    }

    @Override
    public void deleteIconicPlace(Long id) {
        if (iconicPlaceRepository.existsById(id)) {
            iconicPlaceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Lugar icónico no encontrado");
        }
    }

    @Override
    public SiteResponseDTO getSite(Long id) {

        Site site = siteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Site no encontrado"));

        return convertToSiteResponseDTO(site);
    }

    private SiteResponseDTO convertToSiteResponseDTO(Site site) {
        SiteResponseDTO dtosite = new SiteResponseDTO();
        dtosite.setIdVereda(site.getIdSite());
        dtosite.setNombreVereda(site.getName());
        dtosite.setDescripcion(site.getDescription());
        return dtosite;
    }

    @Override
    public List<IconicPlaceResponseDTO> getAllIconicPlaces() {
        return iconicPlaceRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .toList();
    }

    private IconicPlaceResponseDTO convertToResponseDTO(Iconic_place iconicPlace) {
        IconicPlaceResponseDTO dto = new IconicPlaceResponseDTO();
        dto.setIdLugar(iconicPlace.getIdsite().getIdSite());
        dto.setNombreLugar(iconicPlace.getName());
        dto.setIndicaciones(iconicPlace.getDescription());
        // Mapea aquí los demás campos que tengas en tu DTO
        if (dto.getIdLugar() != null) {
            dto.setNombreLugar(iconicPlace.getIdsite().getName());
        }
        return dto;
    }

}
