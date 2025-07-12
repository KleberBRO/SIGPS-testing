// src/main/java/com/ufrpe/sigps/controller/AdminController.java
package com.ufrpe.sigps.controller;

import com.ufrpe.sigps.dto.auth.RegisterRequest;
import com.ufrpe.sigps.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @PostMapping("/users/create-admin")
    public ResponseEntity<Void> createAdminUser(@RequestBody RegisterRequest request) {
        authService.createAdmin(request);
        return ResponseEntity.created(null).build();
    }
}