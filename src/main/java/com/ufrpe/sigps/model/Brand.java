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
@Table(name = "brand")
@DiscriminatorValue("MARCA")
public class Brand extends IntellectualProperty {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrandType brandType;

    @Column(name = "brand_name")
    private String brandName; // Se aplicável (nominativa, mista)

    @Column(name = "image_url") // Caminho para a imagem (se figurativa, mista, tridimensional)
    private String imageUrl;

    @Column(name = "nice_classification_code", nullable = false)
    private String niceClassificationCode;

    @Column(name = "activity_description", columnDefinition = "TEXT", nullable = false)
    private String activityDescription;

    @Column(name = "request_nature", nullable = false) // Marca nova ou com prioridade unionista
    private String requestNature;

    @Column(name = "usage_status", nullable = false) // Já em uso ou futuro
    private String usageStatus;

    @Column(name = "start_usage_date")
    private LocalDate startUsageDate; // Opcional
}