package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "software")
@DiscriminatorValue("SOFTWARE")
public class Software extends IntellectualProperty {

    @Column(name = "holder_name", nullable = false)
    private String holderName; // Nome do titular

    @Column(name = "holder_address", nullable = false)
    private String holderAddress;

    @Column(name = "holder_cpf_cnpj", nullable = false)
    private String holderCpfCnpj;

    @Column(name = "authors_data", columnDefinition = "TEXT", nullable = false)
    private String authorsData; // Dados dos autores (pode ser uma lista serializada ou um texto descritivo)

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "publication_date")
    private LocalDate publicationDate; // Pode ser nulo

    @Column(name = "programming_language", nullable = false)
    private String programmingLanguage;

    @Column(name = "application_field", nullable = false)
    private String applicationField;

    @Column(name = "program_type", nullable = false)
    private String programType;

    @Column(name = "algorithm", columnDefinition = "TEXT")
    private String algorithm;

    @Column(name = "hash_description")
    private String hashDescription;

    @Column(name = "authorized_derivation", nullable = false)
    private Boolean authorizedDerivation; // Se houve derivação autorizada

    @Column(name = "source_code_path", columnDefinition = "TEXT", nullable = false) // Caminho para o código-fonte
    private String sourceCodePath;

    // Documentos obrigatórios (DV e procuração) podem ser links para Document, ou apenas um campo booleano de confirmação
    // para simplificar, usaremos o relacionamento OneToMany com Document.java.
}