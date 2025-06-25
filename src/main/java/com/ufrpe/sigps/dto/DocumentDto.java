package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {
    private Long id;
    @NotBlank(message = "Título do documento é obrigatório")
    private String title;
    @NotBlank(message = "Tipo do documento é obrigatório")
    private String type;
    @NotBlank(message = "Caminho do arquivo é obrigatório")
    private String filePath;
}
