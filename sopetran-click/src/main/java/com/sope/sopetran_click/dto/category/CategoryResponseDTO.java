package com.sope.sopetran_click.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long   idCategory;
    private String name;
    private String description;
}