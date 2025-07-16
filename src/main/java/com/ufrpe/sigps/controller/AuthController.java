// src/main/java/com/ufrpe/sigps/controller/AuthController.java
package com.ufrpe.sigps.controller;

// --- INÍCIO DAS IMPORTAÇÕES CORRIGIDAS ---
import com.ufrpe.sigps.dto.auth.AuthRequest;
import com.ufrpe.sigps.dto.auth.AuthResponse;
import com.ufrpe.sigps.dto.auth.RegisterRequest;
// --- FIM DAS IMPORTAÇÕES CORRIGIDAS ---

import com.ufrpe.sigps.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request // Agora usa a classe do pacote "auth"
    ) {
        // O `service.register` espera um RegisterRequest do pacote "auth",
        // e agora o controller está passando o tipo correto.
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request // Agora usa a classe do pacote "auth"
    ) {
        // O mesmo se aplica aqui para o AuthRequest e AuthResponse.
        return ResponseEntity.ok(service.authenticate(request));
    }

}