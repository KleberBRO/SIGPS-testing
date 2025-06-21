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
@Table(name = "cultivar")
@DiscriminatorValue("CULTIVAR")
public class Cultivar extends IntellectualProperty {

    @Column(name = "cultivar_name", nullable = false)
    private String cultivarName;

    @Column(name = "botanical_species", nullable = false)
    private String botanicalSpecies;

    @Column(name = "commercial_denomination")
    private String commercialDenomination;

    @Column(nullable = false)
    private String origin; // Cruzamento, seleção, etc.

    @Column(name = "distinctive_characteristics", columnDefinition = "TEXT", nullable = false)
    private String distinctiveCharacteristics;

    @Column(name = "purpose_of_use", nullable = false)
    private String purposeOfUse;

    @Column(name = "country_of_origin", nullable = false)
    private String countryOfOrigin;

    @Column(name = "protection_type", nullable = false)
    private String protectionType;

    @Column(name = "creation_development_date", nullable = false)
    private LocalDate creationDevelopmentDate;

    @Column(name = "dhe_data", columnDefinition = "TEXT")
    private String dheData; // Dados relacionados à DHE (Distinguibilidade, Homogeneidade e Estabilidade)
}
