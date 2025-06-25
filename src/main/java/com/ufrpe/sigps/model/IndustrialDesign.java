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
@Table(name = "industrial_design")
@DiscriminatorValue("DESENHO_INDUSTRIAL")
public class IndustrialDesign extends IntellectualProperty {

    @Column(name = "locarno_classification", nullable = false)
    private String locarnoClassification;

    @Column(name = "number_of_variations", nullable = false)
    private Integer numberOfVariations;

    @Column(name = "design_creation_date", nullable = false)
    private LocalDate designCreationDate;

    @Column(name = "application_field", nullable = false)
    private String applicationField;

    @Column(name = "unionist_priority")
    private Boolean unionistPriority;
}