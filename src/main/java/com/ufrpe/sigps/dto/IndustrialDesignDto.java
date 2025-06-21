package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class IndustrialDesignDto extends IntellectualPropertyDto {
    @NotBlank(message = "Classificação de Locarno é obrigatória")
    private String locarnoClassification;
    @NotNull(message = "Número de variações é obrigatório")
    @PositiveOrZero(message = "Número de variações deve ser maior ou igual a zero")
    private Integer numberOfVariations;
    @NotNull(message = "Data de criação do desenho é obrigatória")
    private LocalDate designCreationDate;
    @NotBlank(message = "Campo de aplicação é obrigatório")
    private String applicationField;
    private Boolean unionistPriority;
}