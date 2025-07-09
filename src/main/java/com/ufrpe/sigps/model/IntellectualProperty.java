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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "pi_type", discriminatorType = DiscriminatorType.STRING)
public abstract class IntellectualProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "pi_type", insertable = false, updatable = false)
    private IntellectualPropertyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IntellectualPropertyStatus status;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "grant_date")
    private LocalDate grantDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventor_id", nullable = false)
    private Inventor inventor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id")
    private Startup startup;

    @OneToMany(mappedBy = "intellectualProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilePi> files;

    @Column(name = "processing_stage")
    private String processingStage;
}