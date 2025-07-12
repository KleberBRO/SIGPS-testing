// src/main/java/com/ufrpe/sigps/config/DataInitializer.java
package com.ufrpe.sigps.config;

import com.ufrpe.sigps.model.Administrator;
import com.ufrpe.sigps.model.Role;
import com.ufrpe.sigps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe algum usuário com o papel de ADMIN
        if (!userRepository.existsByRole(Role.ADMIN)) {
            System.out.println("Nenhum administrador encontrado. Criando usuário ADMIN padrão...");

            Administrator defaultAdmin = Administrator.builder()
                    .name("Administrador Padrão")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .cpf("000.000.000-00") // Dados genéricos para o admin inicial
                    .address("N/A")
                    .nationality("N/A")
                    .dateBirth(LocalDate.now())
                    .build();

            userRepository.save(defaultAdmin);
            System.out.println("Administrador padrão criado com sucesso.");
        } else {
            System.out.println("Um administrador já existe. Nenhuma ação necessária.");
        }
    }
}
