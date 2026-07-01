package com.sope.sopetran_click.dto.accommodation;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para recibir la información de registro o actualización de una Finca.
 * Incluye validaciones declarativas que activan el GlobalExceptionHandler ante errores.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstateRequestDTO {

    @NotNull(message = "You need to add the accommodation ID.")
    private Long idAccommodation; // ID de la relación con Accommodations

    @NotBlank(message = "The name can not be empty.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 letters.")
    private String nombre;

    @NotBlank(message = "You need to write the type of place.")
    @Size(max = 50, message = "The type can not have more than 50 letters.")
    private String tipoFinca; // 'finca recreativa', 'casa campestre'

    @NotBlank(message = "The description can not be empty.")
    @Size(max = 1000, message = "The description can not have more than 1000 letters.")
    private String descripcion;

    @NotBlank(message = "You need to add the address.")
    @Size(max = 255, message = "The address can not have more than 255 letters.")
    private String ubicacion;

    @NotNull(message = "You need to add the price per night.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be more than 0.")
    private BigDecimal precioPorNoche;

    @NotNull(message = "You need to add the max number of people.")
    @Min(value = 1, message = "The place must have space for at least 1 person.")
    @Max(value = 200, message = "The place can not have more than 200 people.")
    private Integer capacidad;

    @NotBlank(message = "You need to add the main image.")
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "The image link is not correct (example: http://... or https://...).")
    private String imageUrl;

    @NotBlank(message = "You need to add a phone or contact.")
    @Size(min = 7, max = 20, message = "The contact must have between 7 and 20 letters or numbers.")
    private String contacto;
}
