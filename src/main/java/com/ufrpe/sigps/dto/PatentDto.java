package com.ufrpe.sigps.dto;

import com.ufrpe.sigps.model.PatentType;
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
public class PatentDto extends IntellectualPropertyDto {
    @NotNull(message = "Tipo de patente é obrigatório")
    private PatentType patentType;
    @NotBlank(message = "Classificação internacional (IPC) é obrigatória")
    private String internationalClassification;
    @NotBlank(message = "Campo técnico de aplicação é obrigatório")
    private String technicalApplicationField;
    @NotNull(message = "Data de depósito é obrigatória")
    private LocalDate filingDate;
    private LocalDate priorityDate;
    private String priorityCountry;
    private String priorityNumber;
    private Boolean previousRequestRelated;
}
