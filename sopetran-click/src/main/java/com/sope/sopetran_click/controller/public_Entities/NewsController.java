package com.sope.sopetran_click.controller.public_Entities;


import com.sope.sopetran_click.dto.public_Entities.NewsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.NewsResponseDTO;
import com.sope.sopetran_click.service.public_Entities.NewsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        List<NewsResponseDTO> news = newsService.listarTodos();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNews(@PathVariable Long id) {
        NewsResponseDTO news = newsService.buscarPorId(id);
        return ResponseEntity.ok(news);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> createNews(@Valid @RequestBody NewsRequestDTO dto) {
        NewsResponseDTO news = newsService.crear(dto);
        return new ResponseEntity<>(news, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequestDTO dto) {
        NewsResponseDTO news = newsService.actualizar(id, dto);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
