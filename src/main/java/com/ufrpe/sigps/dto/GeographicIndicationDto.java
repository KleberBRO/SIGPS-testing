package com.ufrpe.sigps.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GeographicIndicationDto extends IntellectualPropertyDto {
    @NotBlank(message = "Nome geográfico é obrigatório")
    private String geographicName;
    @NotBlank(message = "Produto ou serviço atrelado é obrigatório")
    private String product;
    @NotBlank(message = "Delimitação da área é obrigatória")
    private String areaDelimitation;
    @NotBlank(message = "Natureza da IG é obrigatória")
    private String igNature;
    private String visualRepresentationUrl;
}