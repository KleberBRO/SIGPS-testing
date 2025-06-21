package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Patent;
import com.ufrpe.sigps.model.PatentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatentRepository extends JpaRepository<Patent, Long> {
    List<Patent> findByPatentType(PatentType patentType);
    List<Patent> findByInternationalClassification(String internationalClassification);
}