package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.IntellectualProperty;
import com.ufrpe.sigps.model.IntellectualPropertyStatus;
import com.ufrpe.sigps.model.IntellectualPropertyType;
import com.ufrpe.sigps.model.Inventor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IntellectualPropertyRepository extends JpaRepository<IntellectualProperty, Long> {
    // Encontra todas as PIs de um inventor específico
    List<IntellectualProperty> findByInventor(Inventor inventor);

    // Encontra PIs por status
    List<IntellectualProperty> findByStatus(IntellectualPropertyStatus status);

    // Encontra PIs por tipo
    List<IntellectualProperty> findByType(IntellectualPropertyType type);

    // Encontra PIs por título (ignorando caixa e com correspondência parcial)
    List<IntellectualProperty> findByTitleContainingIgnoreCase(String title);

    // Encontra PIs dentro de um período de data de solicitação
    List<IntellectualProperty> findByRequestDateBetween(LocalDate startDate, LocalDate endDate);

    // Você pode criar repositórios específicos para cada tipo de PI se precisar de métodos muito específicos
    // para Marca, Software, etc. Por enquanto, a interface base é suficiente, pois a herança do JPA já cuida disso.
}
