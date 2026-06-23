package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.ReviewsRequestDTO;
import com.sope.sopetran_click.dto.users.ReviewsResponseDTO;
import java.util.List;

public interface ReviewService {
    ReviewsResponseDTO createReview(ReviewsRequestDTO requestDTO);
    ReviewsResponseDTO getReviewById(Long id);
    List<ReviewsResponseDTO> getAllReviews();
    ReviewsResponseDTO updateReview(Long id, ReviewsRequestDTO requestDTO);
    void deleteReview(Long id);

    // Búsquedas específicas
    List<ReviewsResponseDTO> getReviewsByUserId(Long idUser);
    List<ReviewsResponseDTO> getReviewsByCategoryId(Long idCategory);
}