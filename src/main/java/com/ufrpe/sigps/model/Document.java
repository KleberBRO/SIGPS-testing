package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // Ex: "Procuração", "Código-Fonte", "Imagem", "Caderno de Especificações"

    @Column(nullable = false)
    private String filePath; // Caminho ou URL para o arquivo armazenado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intellectual_property_id") // Documentos podem ser associados a PIs
    private IntellectualProperty intellectualProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id") // Documentos também podem ser associados a Startups
    private Startup startup;
}