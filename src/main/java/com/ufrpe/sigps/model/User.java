package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// IMPORTS DO SPRING SECURITY (ADICIONE ESTES)
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // ESTE É O MAIS IMPORTANTE!

import java.time.LocalDate;
import java.util.Collection;
import java.util.List; // Importar List para o método getAuthorities()

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "_user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails { // <-- AGORA IMPLEMENTA USERDETAILS!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password; // Será armazenada a senha hash

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ADMIN ou INVENTOR

    // --- MÉTODOS DA INTERFACE USERDETAILS (ADICIONE ESTES) ---
    // Estes métodos fornecem informações sobre o usuário para o Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a Role do seu sistema para uma GrantedAuthority do Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // O email será o "username" que o Spring Security usará para autenticar
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Retorna true se a conta do usuário não está expirada
        return true; // Para o SIGPS, assumimos que as contas não expiram automaticamente
    }

    @Override
    public boolean isAccountNonLocked() {
        // Retorna true se a conta do usuário não está bloqueada
        return true; // Para o SIGPS, assumimos que as contas não são bloqueadas automaticamente
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Retorna true se as credenciais (senha) do usuário não estão expiradas
        return true; // Para o SIGPS, assumimos que as credenciais não expiram automaticamente
    }

    @Override
    public boolean isEnabled() {
        // Retorna true se o usuário está habilitado
        return true; // Para o SIGPS, assumimos que os usuários estão sempre habilitados após o registro
    }

    // O método getPassword() já é gerado pelo Lombok devido à anotação @Data no campo 'password'.
}