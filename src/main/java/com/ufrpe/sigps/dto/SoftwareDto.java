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
public class SoftwareDto extends IntellectualPropertyDto {
    @NotBlank(message = "Nome do titular é obrigatório")
    private String holderName;
    @NotBlank(message = "Endereço do titular é obrigatório")
    private String holderAddress;
    @NotBlank(message = "CPF/CNPJ do titular é obrigatório")
    private String holderCpfCnpj;
    @NotBlank(message = "Dados dos autores são obrigatórios")
    private String authorsData;
    @NotNull(message = "Data de criação é obrigatória")
    private LocalDate creationDate;
    private LocalDate publicationDate;
    @NotBlank(message = "Linguagem de programação é obrigatória")
    private String programmingLanguage;
    @NotBlank(message = "Campo de aplicação é obrigatório")
    private String applicationField;
    @NotBlank(message = "Tipo de programa é obrigatório")
    private String programType;
    private String algorithm;
    private String hashDescription;
    @NotNull(message = "Derivação autorizada é obrigatória")
    private Boolean authorizedDerivation;
    @NotBlank(message = "Caminho do código-fonte é obrigatório")
    private String sourceCodePath;
}