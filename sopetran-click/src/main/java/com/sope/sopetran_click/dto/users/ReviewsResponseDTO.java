package com.sope.sopetran_click.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsResponseDTO {
    private Long idReview;
    private String userName;
    private String categoryName;
    private Long idItemEspecifico;
    private String texto;
    private Integer calificacion;
    private LocalDateTime fechaComentario;
}
