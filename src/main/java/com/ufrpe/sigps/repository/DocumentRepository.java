package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Document;
import com.ufrpe.sigps.model.IntellectualProperty;
import com.ufrpe.sigps.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByIntellectualProperty(IntellectualProperty intellectualProperty);

    List<Document> findByStartup(Startup startup);

    List<Document> findByType(String type);
}