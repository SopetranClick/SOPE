package com.sope.sopetran_click.repository;

import com.sope.sopetran_click.model.Users; // Asegúrate de que tu clase en Model se llame User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // 🔐 Para el Login: Spring genera un "SELECT * FROM usuarios WHERE email = ?"
    Optional<Users> findByEmail(String email);

    // 🚫 Para el Registro: Spring revisa si el correo ya existe antes de crear la cuenta
    boolean existsByEmail(String email);
}