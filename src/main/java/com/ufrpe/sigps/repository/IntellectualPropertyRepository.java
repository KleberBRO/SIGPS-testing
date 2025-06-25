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

    List<IntellectualProperty> findByInventor(Inventor inventor);


    List<IntellectualProperty> findByStatus(IntellectualPropertyStatus status);


    List<IntellectualProperty> findByType(IntellectualPropertyType type);


    List<IntellectualProperty> findByTitleContainingIgnoreCase(String title);


    List<IntellectualProperty> findByRequestDateBetween(LocalDate startDate, LocalDate endDate);

}
