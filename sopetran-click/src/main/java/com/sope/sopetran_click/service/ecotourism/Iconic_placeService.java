package com.sope.sopetran_click.service.ecotourism;

import com.sope.sopetran_click.dto.ecotourism.IconicPlaceResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;

import java.util.List;

public interface Iconic_placeService {

    IconicPlaceResponseDTO getIconicPlace(Long id);
    IconicPlaceResponseDTO createIconicPlace(IconicPlaceResponseDTO iconicPlace);
    IconicPlaceResponseDTO updateIconicPlace(Long id, IconicPlaceResponseDTO iconicPlace);
    void deleteIconicPlace(Long id);
    SiteResponseDTO getSite(Long id);

    List<IconicPlaceResponseDTO> getAllIconicPlaces();
}
