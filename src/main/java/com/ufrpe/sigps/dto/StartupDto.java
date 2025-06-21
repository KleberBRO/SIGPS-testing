package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartupDto {
    private Long id;
    @NotBlank(message = "CNPJ ou identificador é obrigatório")
    private String cnpjOrIdentifier;
    @NotBlank(message = "Nome da startup é obrigatório")
    private String name;
    private String description;
    @NotNull(message = "Data de criação é obrigatória")
    private LocalDate creationDate;
    @NotBlank(message = "Status da startup é obrigatório")
    private String status;
    private String academicProjectLink;
    private String researchGroupLink;
    // Opcional: List<IntellectualPropertyDto> intellectualProperties;
    // List<DocumentDto> documents;
    // Cuidado com ciclos ou dados excessivos.
}