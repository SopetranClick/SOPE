// ─── CategoryController.java ──────────────────────────────────────────────────
package com.sope.sopetran_click.controller;

import com.sope.sopetran_click.dto.category.CategoryRequestDTO;
import com.sope.sopetran_click.dto.category.CategoryResponseDTO;
import com.sope.sopetran_click.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoryService.listarTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.buscarPorId(id));
    }


    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoryResponseDTO> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoryService.buscarPorNombre(nombre));
    }


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> crear(
            @Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO nueva = categoryService.crear(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoryService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}