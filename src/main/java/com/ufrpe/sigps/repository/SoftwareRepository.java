package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    List<Software> findByProgrammingLanguage(String programmingLanguage);
    List<Software> findByApplicationField(String applicationField);
}