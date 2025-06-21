package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "intellectual_property")
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança para os tipos de PI
@DiscriminatorColumn(name = "pi_type", discriminatorType = DiscriminatorType.STRING) // Coluna para diferenciar tipos de PI
public abstract class IntellectualProperty { // Abstract, pois PI base não será instanciada diretamente

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único da PI

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // TEXT para descrições longas
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "pi_type", insertable = false, updatable = false) // Mapeia para a coluna do DiscriminatorColumn
    private IntellectualPropertyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IntellectualPropertyStatus status;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "grant_date")
    private LocalDate grantDate; // Pode ser nulo se ainda não concedida

    @Column(name = "expiration_date")
    private LocalDate expirationDate; // Pode ser nulo se não aplicável ou não concedida

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para o inventor
    @JoinColumn(name = "inventor_id", nullable = false) // Coluna de chave estrangeira
    private Inventor inventor; // Cada PI pertence a um inventor

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para a startup
    @JoinColumn(name = "startup_id") // Pode ser nulo se não estiver vinculada a uma startup
    private Startup startup; // Pode ou não estar vinculada a uma startup

    @OneToMany(mappedBy = "intellectualProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents; // Documentos associados à PI

    // Campo para o estágio do processo de registro (para P.I. em processamento)
    // Este campo pode ser um enum ou uma string que represente o estágio atual.
    @Column(name = "processing_stage")
    private String processingStage;
}