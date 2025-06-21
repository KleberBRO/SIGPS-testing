package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface StartupRepository extends JpaRepository<Startup, Long> {
    Optional<Startup> findByCnpjOrIdentifier(String cnpjOrIdentifier); // Encontra uma startup pelo CNPJ/identificador
    List<Startup> findByNameContainingIgnoreCase(String name); // Encontra startups por nome (parcial e ignorando caixa)
    List<Startup> findByStatus(String status); // Encontra startups por status
}