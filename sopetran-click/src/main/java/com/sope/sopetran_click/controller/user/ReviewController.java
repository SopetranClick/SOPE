package com.sope.sopetran_click.controller.user;

import com.sope.sopetran_click.dto.users.ReviewsRequestDTO;
import com.sope.sopetran_click.dto.users.ReviewsResponseDTO;
import com.sope.sopetran_click.service.user.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewsResponseDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewsResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ReviewsResponseDTO>> getByCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(reviewService.getReviewsByCategoryId(categoryId));
    }

    @PostMapping
    public ResponseEntity<ReviewsResponseDTO> createReview(
            @Valid @RequestBody ReviewsRequestDTO dto) {
        return new ResponseEntity<>(reviewService.createReview(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewsResponseDTO> updateReview(
            @PathVariable Long id, @Valid @RequestBody ReviewsRequestDTO dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}