package com.sope.sopetran_click.dto.users;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "The user name can not be empty.")
    @Size(min = 3, max = 50, message = "The user name must have between 3 and 50 letters.")
    private String nombreUsuario;

    @NotBlank(message = "You need to add the email.")
    @Email(message = "The email is not correct (example: name@mail.com).")
    private String email;

    @NotBlank(message = "You need to add the password.")
    @Size(min = 6, max = 50, message = "The password must have between 6 and 50 letters or numbers.")
    private String password;

    @NotBlank(message = "You need to confirm the password.")
    private String confirmPassword; // Útil para validar doble tipeo en el frontend

    @NotBlank(message = "You need to add a phone number.")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "The phone number must have only numbers, between 7 and 15 digits.")
    private String telefono;
}
