package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.IndustrialDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IndustrialDesignRepository extends JpaRepository<IndustrialDesign, Long> {
    List<IndustrialDesign> findByLocarnoClassification(String locarnoClassification);
    List<IndustrialDesign> findByApplicationField(String applicationField);
}