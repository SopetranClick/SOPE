package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.ReviewsRequestDTO;
import com.sope.sopetran_click.dto.users.ReviewsResponseDTO;
import com.sope.sopetran_click.model.Categorys;
import com.sope.sopetran_click.model.Users;
import com.sope.sopetran_click.model.user.Reviews;
import com.sope.sopetran_click.repository.CategorysRepository;
import com.sope.sopetran_click.repository.ReviewRepository; // Asumiendo este nombre
import com.sope.sopetran_click.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CategorysRepository categoryRepository;

    @Override
    @Transactional
    public ReviewsResponseDTO createReview(ReviewsRequestDTO dto) {

        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Reviews review = new Reviews();
        review.setIdUsers(user);
        review.setIdCategory(category);
        review.setIdItems(dto.getIdItemEspecifico());
        review.setDescription(dto.getTexto());

        Reviews reviewsCreate = reviewRepository.save(review);

        return mapToDTO(reviewsCreate);
    }

    @Override
    public ReviewsResponseDTO getReviewById(Long id) {
        return mapToDTO(reviewRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ReviewsResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewsResponseDTO updateReview(Long id, ReviewsRequestDTO dto) {
        Reviews review = reviewRepository.findById(id).orElseThrow();
        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        review.setIdUsers(user);
        review.setIdCategory(category);
        review.setIdItems(dto.getIdItemEspecifico());

        review.setDescription(dto.getTexto());

        Reviews reviewsUpdate = reviewRepository.save(review);
        return mapToDTO(reviewsUpdate);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new RuntimeException("Review not found");
        }
    }

    @Override
    public List<ReviewsResponseDTO> getReviewsByUserId(Long idUser) {
        return reviewRepository.findByIdUsers_IdUsers(idUser).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewsResponseDTO> getReviewsByCategoryId(Long idCategory) {
        return reviewRepository.findByIdCategory_IdCategory(idCategory).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Método auxiliar de mapeo
    private ReviewsResponseDTO mapToDTO(Reviews review) {
        return new ReviewsResponseDTO(
                review.getIdReviews(),
                review.getIdUsers().getName(), // Ajusta según tu modelo Users
                review.getIdCategory().getName(),  // Ajusta según tu modelo Categorys
                review.getIdItems(),
                review.getDescription(),
                0, // Aquí deberías mapear tu campo de calificación si existe en la entidad
                review.getFechaPublicacion()
        );
    }
}