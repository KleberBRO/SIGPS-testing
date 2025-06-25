package com.ufrpe.sigps.controller;

import com.ufrpe.sigps.dto.UserDto;
import com.ufrpe.sigps.dto.RegisterRequest; // Para o caso de admin registrar inventor
import com.ufrpe.sigps.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint para obter todos os usuários (apenas para ADMIN)
     * GET /api/v1/users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Endpoint para obter um usuário por ID (apenas para ADMIN ou o próprio usuário)
    */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Endpoint para o próprio usuário obter seu próprio ID
    */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedInUser() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /** Endpoint para Administrador registrar um novo Inventor
    */
    @PostMapping("/register-inventor")
    public ResponseEntity<UserDto> registerInventorByAdmin(
            @RequestBody @Valid RegisterRequest request,
            @RequestParam String course,
            @RequestParam String department
    ) {
        UserDto newInventor = userService.registerInventorByAdmin(request, course, department);
        return new ResponseEntity<>(newInventor, HttpStatus.CREATED);
    }

    /** Endpoint para atualizar dados de um usuário (apenas para ADMIN ou o próprio usuário)
    */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserDetails(
            @PathVariable Long id,
            @RequestBody @Valid UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUserDetails(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /** Endpoint para Administrador deletar um usuário
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    /** Endpoint para alterar a senha do próprio usuário
     */
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestParam String newPassword) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}