package com.ufrpe.sigps.controller;

import com.ufrpe.sigps.dto.AuthResponse;
import com.ufrpe.sigps.dto.LoginRequest;
import com.ufrpe.sigps.dto.RegisterRequest;
import com.ufrpe.sigps.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Endpoint para registrar um novo usuário.
     * Recebe os dados do usuário no corpo da requisição e retorna um token JWT.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    /**
     * Endpoint para autenticar um usuário existente.
     * Recebe e-mail e senha e retorna um token JWT válido.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
