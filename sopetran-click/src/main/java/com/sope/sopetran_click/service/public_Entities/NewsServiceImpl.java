package com.sope.sopetran_click.service.public_Entities;

import com.sope.sopetran_click.dto.public_Entities.NewsRequestDTO;
import com.sope.sopetran_click.dto.public_Entities.NewsResponseDTO;
import com.sope.sopetran_click.model.category.public_Entities.News;
import com.sope.sopetran_click.model.category.public_Entities.Public_Entitie;
import com.sope.sopetran_click.repository.NewsRepository;
import com.sope.sopetran_click.repository.PublicEntitieRepository;
import com.sope.sopetran_click.service.public_Entities.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final PublicEntitieRepository publicEntitieRepository;

    @Override
    @Transactional
    public NewsResponseDTO crear(NewsRequestDTO dto) {
        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new IllegalArgumentException("Entidad Pública no encontrada"));

        News news = new News();
        news.setIdPublicEntitie(publicEntitie);
        news.setTitle(dto.getTitulo());
        news.setDescription(dto.getContenido());
        news.setFechaPublicacion(LocalDateTime.now()); // Asignar fecha actual al crear

        News saved = newsRepository.save(news);
        return mapearAResponse(saved);
    }

    @Override
    @Transactional
    public NewsResponseDTO actualizar(Long id, NewsRequestDTO dto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Noticia no encontrada con ID: " + id));

        Public_Entitie publicEntitie = publicEntitieRepository.findById(dto.getIdPublicEntitie())
                .orElseThrow(() -> new IllegalArgumentException("Entidad Pública no encontrada"));

        // Mapeo explícito
        news.setIdPublicEntitie(publicEntitie);
        news.setTitle(dto.getTitulo());
        news.setDescription(dto.getContenido());
        // Se mantiene la fecha original o se actualiza si fuera necesario

        News updated = newsRepository.save(news);
        return mapearAResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsResponseDTO buscarPorId(Long id) {
        return newsRepository.findById(id)
                .map(this::mapearAResponse)
                .orElseThrow(() -> new IllegalArgumentException("Noticia no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsResponseDTO> listarTodos() {
        return newsRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar, Noticia no encontrada con ID: " + id);
        }
        newsRepository.deleteById(id);
    }

    private NewsResponseDTO mapearAResponse(News n) {
        return new NewsResponseDTO(
                n.getIdNews(),
                n.getTitle(),
                n.getDescription(),
                n.getFechaPublicacion(),
                n.getIdPublicEntitie() != null ? n.getIdPublicEntitie().getDescription() : null
        );
    }
}