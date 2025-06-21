package com.ufrpe.sigps.dto;

import com.ufrpe.sigps.model.BrandType;
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
public class BrandDto extends IntellectualPropertyDto {
    @NotNull(message = "Tipo de marca é obrigatório")
    private BrandType brandType;

    private String brandName;
    private String imageUrl;

    @NotBlank(message = "Código de classificação de Nice é obrigatório")
    private String niceClassificationCode;

    @NotBlank(message = "Descrição da atividade é obrigatória")
    private String activityDescription;

    @NotBlank(message = "Natureza da solicitação é obrigatória")
    private String requestNature;

    @NotBlank(message = "Status de uso da marca é obrigatório")
    private String usageStatus;

    private LocalDate startUsageDate;
}