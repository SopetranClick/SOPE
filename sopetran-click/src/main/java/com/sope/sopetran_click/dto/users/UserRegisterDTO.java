package com.sope.sopetran_click.dto.users;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    private String nombreUsuario;
    private String email;
    private String password;
    private String confirmPassword; // Útil para validar doble tipeo en el frontend
    private String telefono;
}