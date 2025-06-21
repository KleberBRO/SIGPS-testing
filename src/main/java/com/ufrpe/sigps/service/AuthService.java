package com.ufrpe.sigps.service;

import com.ufrpe.sigps.config.JwtService;
import com.ufrpe.sigps.dto.AuthResponse;
import com.ufrpe.sigps.dto.LoginRequest;
import com.ufrpe.sigps.dto.RegisterRequest;
import com.ufrpe.sigps.exception.ResourceNotFoundException;
import com.ufrpe.sigps.model.Role;
import com.ufrpe.sigps.model.User;
import com.ufrpe.sigps.model.Inventor; // Importar Inventor
import com.ufrpe.sigps.repository.InventorRepository; // Importar InventorRepository
import com.ufrpe.sigps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails; // Importar UserDetails
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final InventorRepository inventorRepository; // Injetar InventorRepository
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Validação inicial (algumas validações já estão no DTO com @Valid)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (userRepository.existsByCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        // Por padrão, novos registros são Inventores
        var inventor = Inventor.builder()
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .nationality(request.getNationality())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword())) // Criptografa a senha
                .role(Role.INVENTOR)
                // Para simplificar o registro inicial de inventor, defina valores padrão ou nulos
                // para 'course' e 'department'. Estes podem ser atualizados posteriormente.
                .course("A definir") // Valor padrão
                .department("A definir") // Valor padrão
                .build();

        inventorRepository.save(inventor); // Salva o inventor no banco de dados

        // Gera o token JWT para o novo usuário registrado
        var jwtToken = jwtService.generateToken(inventor); // inventor é um UserDetails
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(LoginRequest request) {
        // Tenta autenticar o usuário com as credenciais fornecidas
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // Se a autenticação foi bem-sucedida, busca o usuário e gera o token
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + request.getEmail()));

        var jwtToken = jwtService.generateToken(user); // user é um UserDetails
        return AuthResponse.builder().token(jwtToken).build();
    }
}