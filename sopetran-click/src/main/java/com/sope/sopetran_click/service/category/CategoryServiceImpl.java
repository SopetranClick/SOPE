package com.sope.sopetran_click.service.category;

import com.sope.sopetran_click.dto.category.CategoryRequestDTO;
import com.sope.sopetran_click.dto.category.CategoryResponseDTO;
import com.sope.sopetran_click.model.Categorys;
import com.sope.sopetran_click.repository.CategorysRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategorysRepository categoryRepository;

    public CategoryServiceImpl(CategorysRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDTO crear(CategoryRequestDTO dto) {
        // Validar que no exista ya una categoría con ese nombre
        if (categoryRepository.findByName(dto.getName()) != null) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + dto.getName());
        }

        Categorys categoria = new Categorys();
        categoria.setName(dto.getName());
        categoria.setDescription(dto.getDescription());

        return toResponseDTO(categoryRepository.save(categoria));
    }

    @Override
    @Transactional
    public CategoryResponseDTO actualizar(Long id, CategoryRequestDTO dto) {
        Categorys existente = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        existente.setName(dto.getName());
        existente.setDescription(dto.getDescription());

        return toResponseDTO(categoryRepository.save(existente));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO buscarPorId(Long id) {
        Categorys categoria = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return toResponseDTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO buscarPorNombre(String nombre) {
        // Usa findByName (corregido en CategorysRepository)
        // El original tenía findByNameCategory() que lanzaba PropertyReferenceException
        Categorys categoria = categoryRepository.findByName(nombre);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada con nombre: " + nombre);
        }
        return toResponseDTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> listarTodas() {
        return categoryRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // ─── Mapper privado ───────────────────────────────────────────────────────
    private CategoryResponseDTO toResponseDTO(Categorys c) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setIdCategory(c.getIdCategory());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        return dto;
    }
}