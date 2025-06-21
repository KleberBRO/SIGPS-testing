package com.ufrpe.sigps.dto;

import com.ufrpe.sigps.model.IntellectualPropertyStatus;
import com.ufrpe.sigps.model.IntellectualPropertyType;
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
public abstract class IntellectualPropertyDto { // Abstract, pois será estendido
    private Long id;
    private String title;
    private String description;
    private IntellectualPropertyType type;
    private IntellectualPropertyStatus status;
    private LocalDate requestDate;
    private LocalDate grantDate;
    private LocalDate expirationDate;
    private Long inventorId; // Apenas o ID do inventor
    private String inventorName; // Para exibição
    private Long startupId; // Apenas o ID da startup, se houver
    private String startupName; // Para exibição
    private String processingStage;
    private List<DocumentDto> documents; // Inclui documentos associados
}
