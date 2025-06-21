package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.GeographicIndication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeographicIndicationRepository extends JpaRepository<GeographicIndication, Long> {
    List<GeographicIndication> findByGeographicNameContainingIgnoreCase(String geographicName);
    List<GeographicIndication> findByProductOrService(String productOrService);
}
