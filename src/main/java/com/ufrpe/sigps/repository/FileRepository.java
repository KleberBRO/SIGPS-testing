package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.FileApp;
import com.ufrpe.sigps.model.IntellectualProperty;
import com.ufrpe.sigps.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<FileApp, Long> {
    List<FileApp> findByIntellectualProperty(IntellectualProperty intellectualProperty);

    List<FileApp> findByStartup(Startup startup);

    List<FileApp> findByType(String type);
}