package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CultivarDto extends IntellectualPropertyDto {
    @NotBlank(message = "Nome do cultivar é obrigatório")
    private String cultivarName;
    @NotBlank(message = "Espécie botânica é obrigatória")
    private String botanicalSpecies;
    private String commercialDenomination;
    @NotBlank(message = "Origem é obrigatória")
    private String origin;
    @NotBlank(message = "Características distintivas são obrigatórias")
    private String distinctiveCharacteristics;
    @NotBlank(message = "Finalidade de uso é obrigatória")
    private String purposeOfUse;
    @NotBlank(message = "País de obtenção é obrigatório")
    private String countryOfOrigin;
    @NotBlank(message = "Tipo de proteção é obrigatório")
    private String protectionType;
    @NotNull(message = "Data de criação/desenvolvimento é obrigatória")
    private LocalDate creationDevelopmentDate;
    private String dheData;
}