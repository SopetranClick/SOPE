package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.UserRegisterDTO;
import com.sope.sopetran_click.dto.users.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {
    UserResponseDTO registrarUsuario(UserRegisterDTO dto);
    UserResponseDTO buscarPorId(Long id);
    UserResponseDTO buscarPorEmail(String email);
    Page<UserResponseDTO> listarTodos(Pageable pageable);
}