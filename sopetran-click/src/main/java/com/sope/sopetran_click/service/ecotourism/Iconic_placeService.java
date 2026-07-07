package com.sope.sopetran_click.service.ecotourism;

import com.sope.sopetran_click.dto.ecotourism.IconicPlaceRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.IconicPlaceResponseDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;

import java.util.List;

public interface Iconic_placeService {

    IconicPlaceResponseDTO getIconicPlace(Long id);
    IconicPlaceResponseDTO createIconicPlace(IconicPlaceRequestDTO dto);
    IconicPlaceResponseDTO updateIconicPlace(Long id, IconicPlaceRequestDTO dto);
    void deleteIconicPlace(Long id);
    SiteResponseDTO getSite(Long id);


    List<IconicPlaceResponseDTO> getAllIconicPlaces();

    List<IconicPlaceResponseDTO> listarPorSite(Long idSite);
}
