package com.sope.sopetran_click.controller.user;

import com.sope.sopetran_click.dto.users.UserRegisterDTO;
import com.sope.sopetran_click.dto.users.UserResponseDTO;
import com.sope.sopetran_click.service.user.UsersService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    /** POST /api/users/register → Registra un nuevo usuario */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registrar(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO user = usersService.registrarUsuario(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /** GET /api/users/{id} → Busca usuario por ID */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.buscarPorId(id));
    }

    /** GET /api/users/email/{email} → Busca usuario por email */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usersService.buscarPorEmail(email));
    }

    /** GET /api/users?page=0&size=10 → Lista usuarios con paginación */
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listarTodos(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(usersService.listarTodos(pageable));
    }
}