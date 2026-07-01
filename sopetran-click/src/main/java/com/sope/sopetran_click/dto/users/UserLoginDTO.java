package com.sope.sopetran_click.dto.users;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "You need to add the email.")
    @Email(message = "The email is not correct (example: name@mail.com).")
    private String email;

    @NotBlank(message = "You need to add the password.")
    @Size(min = 6, max = 50, message = "The password must have between 6 and 50 letters or numbers.")
    private String password;
}
