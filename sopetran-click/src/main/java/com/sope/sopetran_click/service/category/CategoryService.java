package com.sope.sopetran_click.service.category;

import com.sope.sopetran_click.dto.category.CategoryRequestDTO;
import com.sope.sopetran_click.dto.category.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {

    CategoryResponseDTO crear(CategoryRequestDTO dto);
    CategoryResponseDTO actualizar(Long id, CategoryRequestDTO dto);
    CategoryResponseDTO buscarPorId(Long id);
    CategoryResponseDTO buscarPorNombre(String nombre);
    List<CategoryResponseDTO> listarTodas();
    void eliminar(Long id);
}