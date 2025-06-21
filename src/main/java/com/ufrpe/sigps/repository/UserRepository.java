package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos personalizados (Spring Data JPA irá implementar automaticamente)
    Optional<User> findByEmail(String email); // Encontra um usuário pelo email
    boolean existsByCpf(String cpf);          // Verifica se um CPF já existe
    boolean existsByEmail(String email);      // Verifica se um email já existe
}