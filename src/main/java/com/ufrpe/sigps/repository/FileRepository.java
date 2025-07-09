package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.FilePi;
import com.ufrpe.sigps.model.IntellectualProperty;
import com.ufrpe.sigps.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<FilePi, Long> {
    List<FilePi> findByIntellectualProperty(IntellectualProperty intellectualProperty);

    List<FilePi> findByStartup(Startup startup);

    List<FilePi> findByType(String type);
}