package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.NewsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.NewsResponseDTO;
import java.util.List;

public interface NewsService {
    NewsResponseDTO crear(NewsRequestDTO dto);
    NewsResponseDTO actualizar(Long id, NewsRequestDTO dto);
    NewsResponseDTO buscarPorId(Long id);
    List<NewsResponseDTO> listarTodos();
    void eliminar(Long id);
}