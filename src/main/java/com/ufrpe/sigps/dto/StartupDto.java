package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartupDto {
    private Long id;
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;
    @NotBlank(message = "Nome da startup é obrigatório")
    private String name;
    private String description;
    @NotNull(message = "Data de criação é obrigatória")
    private LocalDate creationDate;
    @NotBlank(message = "Status da startup é obrigatório")
    private String status;
    private String academicProjectLink;
    private String researchGroupLink;
     private List<IntellectualPropertyDto> intellectualProperties;
     private List<FileDto> files;
     public void addFile(FileDto file) {
        if (this.files == null) {
            this.files = new ArrayList<>();
        }
        this.files.add(file);
    }

}