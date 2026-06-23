package com.sope.sopetran_click.service.ecotourism;

import com.sope.sopetran_click.dto.ecotourism.SiteRequestDTO;
import com.sope.sopetran_click.dto.ecotourism.SiteResponseDTO;

import java.util.List;

public interface SiteService {

    SiteResponseDTO getSite(Long id);

    List<SiteResponseDTO> getAllSites();

    SiteResponseDTO saveSite(SiteRequestDTO site);

    void deleteSite(Long id);

    SiteResponseDTO updateSite(Long id, SiteRequestDTO site);
}
