package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Cultivar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CultivarRepository extends JpaRepository<Cultivar, Long> {
    List<Cultivar> findByBotanicalSpecies(String botanicalSpecies);
    List<Cultivar> findByCountryOfOrigin(String countryOfOrigin);
}