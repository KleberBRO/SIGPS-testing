package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "startup")
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj_or_identifier", nullable = false, unique = true)
    private String cnpjOrIdentifier;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private String status; // Ex: "Ativa", "Incubada", "Graduada"

    @Column(name = "academic_project_link")
    private String academicProjectLink; // Vinculação a projetos acadêmicos (URL ou identificador)

    @Column(name = "research_group_link")
    private String researchGroupLink; // Vinculação a grupos de pesquisa (URL ou identificador)

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IntellectualProperty> intellectualProperties; // Uma startup pode ter várias PIs vinculadas

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents; // Documentos associados à startup
}