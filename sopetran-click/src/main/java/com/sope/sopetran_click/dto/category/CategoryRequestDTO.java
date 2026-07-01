package com.sope.sopetran_click.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    @NotBlank(message = "The category name can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters.")
    private String name;

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 255, message = "The description can not have more than 255 characters.")
    private String description;
}