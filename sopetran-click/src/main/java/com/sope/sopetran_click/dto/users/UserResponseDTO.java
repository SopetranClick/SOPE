package com.sope.sopetran_click.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private String telefono;
    private String rol; // 'turista', 'comerciante', 'admin'
    private LocalDateTime fechaRegistro;
}
