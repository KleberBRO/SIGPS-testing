package com.ufrpe.sigps.service.security;

import com.ufrpe.sigps.dto.auth.AuthResponse;
import com.ufrpe.sigps.dto.auth.RegisterRequest;
import com.ufrpe.sigps.dto.auth.AuthRequest;
import com.ufrpe.sigps.model.Administrator;
import com.ufrpe.sigps.model.Role;
import com.ufrpe.sigps.model.Inventor;
import com.ufrpe.sigps.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Agora construímos o objeto Inventor com todos os dados obrigatórios
        var inventor = Inventor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.INVENTOR)
                .dateBirth(request.getDateBirth())
                .cpf(request.getCpf())
                .nationality(request.getNationality())
                .address(request.getAddress())
                .course(request.getCourse())
                .department(request.getDepartment())
                .build();

        repository.save(inventor);

        var jwtToken = jwtService.generateToken(inventor);
        return AuthResponse.builder().token(jwtToken).build();
    }

    // NOVO MÉTODO para criação de ADMINS
    public void createAdmin(RegisterRequest request) {
        // Você pode adicionar validações aqui, como verificar se o e-mail já existe
        var admin = Administrator.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN) // <-- Papel de ADMIN
                .dateBirth(request.getDateBirth())
                .cpf(request.getCpf())
                .address(request.getAddress())
                .nationality(request.getNationality())
                .build();
        repository.save(admin);
    }

    // O método authenticate NÃO PRECISA DE MUDANÇAS!
    // Ele busca um "User" pelo e-mail, e o polimorfismo do JPA retornará
    // o objeto correto (Inventor ou Admin), que já é um UserDetails.
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        
        // Adicionar informações extras ao token
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("roles", user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList());
        extraClaims.put("userId", user.getId());
        extraClaims.put("name", user.getName());
        
        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}