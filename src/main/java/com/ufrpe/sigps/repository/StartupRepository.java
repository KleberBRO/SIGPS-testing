package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface StartupRepository extends JpaRepository<Startup, Long> {

    boolean existsByCnpj(String cnpj);
    Optional<Startup> findByCnpj(String cnpj);
    List<Startup> findByNameContainingIgnoreCase(String name);
    List<Startup> findByStatus(String status);
}