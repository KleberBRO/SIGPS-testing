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

    // Endpoint para obter todos os usuários (apenas para ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Endpoint para obter um usuário por ID (apenas para ADMIN ou o próprio usuário)
    // Uma lógica mais robusta para "o próprio usuário" seria feita no serviço,
    // mas aqui o @PreAuthorize garante que só ADMIN pode ver qualquer ID.
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Endpoint para o próprio usuário obter seus dados de perfil
    // O usuário logado obtém seu próprio ID via SecurityContextHolder no serviço
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTOR')")
    public ResponseEntity<UserDto> getLoggedInUser() {
        // No mundo real, você passaria o ID do usuário logado (SecurityContextHolder) para o serviço
        // Por simplicidade aqui, vamos simular:
        // Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // return ResponseEntity.ok(userService.getUserById(userId));
        // Por enquanto, apenas um placeholder para o endpoint. A lógica será implementada no serviço quando necessário.
        // **Para este momento, este endpoint apenas serve como um placeholder de rota**
        // A lógica de extrair o ID do usuário logado será no UserService ou em um novo serviço dedicado ao perfil.
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); // Placeholder
    }


    // Endpoint para Administrador registrar um novo Inventor
    @PostMapping("/register-inventor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> registerInventorByAdmin(
            @RequestBody @Valid RegisterRequest request,
            @RequestParam String course,
            @RequestParam String department
    ) {
        UserDto newInventor = userService.registerInventorByAdmin(request, course, department);
        return new ResponseEntity<>(newInventor, HttpStatus.CREATED);
    }

    // Endpoint para atualizar dados de um usuário (apenas para ADMIN ou o próprio usuário)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Ou "hasAnyRole('ADMIN', 'INVENTOR') && #id == authentication.principal.id"
    public ResponseEntity<UserDto> updateUserDetails(
            @PathVariable Long id,
            @RequestBody @Valid UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUserDetails(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint para Administrador deletar um usuário
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint para alterar a senha do próprio usuário
    @PutMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTOR')")
    public ResponseEntity<Void> changePassword(@RequestParam String newPassword) {
        // A lógica de obter o ID do usuário logado deve ser feita aqui ou no serviço.
        // Exemplo: Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // userService.changePassword(userId, newPassword);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); // Placeholder
    }
}