package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.UserRegisterDTO;
import com.sope.sopetran_click.dto.users.UserResponseDTO;
import com.sope.sopetran_click.model.Users;
import com.sope.sopetran_click.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository usersRepository;

    // NOTA: Para producción, inyectar BCryptPasswordEncoder de Spring Security.
    // Aquí usamos una simulación de hash básico por seguridad inicial.
    private String hashPassword(String password) {
        return java.util.Base64.getEncoder().encodeToString(password.getBytes());
    }

    @Override
    @Transactional
    public UserResponseDTO registrarUsuario(UserRegisterDTO dto) {
        // 1. Validar si las contraseñas coinciden
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        // 2. Validar si el email ya existe
        if (usersRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // 3. Mapear DTO de registro a la Entidad Users
        Users usuario = new Users();
        usuario.setName(dto.getNombreUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setPasswordHash(hashPassword(dto.getPassword())); // Encriptación básica
        usuario.setTelefono(dto.getTelefono());
        usuario.setRole(Users.RolUsers.Tourist); // Rol por defecto usando tu enum
        usuario.setFechaRegistro(LocalDateTime.now());

        // 4. Guardar en PostgreSQL
        Users usuarioGuardado = usersRepository.save(usuario);

        // 5. Retornar DTO de Respuesta
        return convertToResponseDTO(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO buscarPorId(Long id) {
        Users usuario = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertToResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO buscarPorEmail(String email) {
        Users usuario = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return convertToResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> listarTodos(Pageable pageable) {
        return usersRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }
    

    // Método auxiliar de mapeo (Entity -> DTO)
    private UserResponseDTO convertToResponseDTO(Users user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setIdUsuario(user.getIdUsers());
        response.setNombreUsuario(user.getName());
        response.setEmail(user.getEmail());
        response.setTelefono(user.getTelefono());
        response.setRol(user.getRole().name());
        response.setFechaRegistro(user.getFechaRegistro());
        return response;
    }
}