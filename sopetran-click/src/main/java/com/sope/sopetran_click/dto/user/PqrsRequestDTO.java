package com.sope.sopetran_click.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PqrsRequestDTO {

    @NotBlank(message = "Debes seleccionar el tipo de solicitud.")
    private String tipo;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 120, message = "El nombre no puede tener más de 120 caracteres.")
    private String nombre;

    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 120, message = "El correo no puede tener más de 120 caracteres.")
    private String email;

    @Size(max = 30, message = "El teléfono no puede tener más de 30 caracteres.")
    private String telefono;

    @NotBlank(message = "El asunto no puede estar vacío.")
    @Size(max = 150, message = "El asunto no puede tener más de 150 caracteres.")
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    private String mensaje;
}
