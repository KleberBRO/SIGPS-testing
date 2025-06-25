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
@Table(name = "patent")
@DiscriminatorValue("PATENTE")
public class Patent extends IntellectualProperty {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatentType patentType;

    @Column(name = "international_classification", nullable = false)
    private String internationalClassification;

    @Column(name = "technical_application_field", nullable = false)
    private String technicalApplicationField;

    @Column(name = "filing_date", nullable = false)
    private LocalDate filingDate;

    @Column(name = "priority_date")
    private LocalDate priorityDate;

    @Column(name = "priority_country")
    private String priorityCountry;

    @Column(name = "priority_number")
    private String priorityNumber;

    @Column(name = "previous_request_related")
    private Boolean previousRequestRelated;
}