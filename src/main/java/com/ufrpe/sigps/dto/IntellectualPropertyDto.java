package com.ufrpe.sigps.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ufrpe.sigps.model.IntellectualPropertyStatus;
import com.ufrpe.sigps.model.IntellectualPropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SoftwareDto.class, name = "SOFTWARE"),
        @JsonSubTypes.Type(value = BrandDto.class, name = "MARCA"),
        @JsonSubTypes.Type(value = PatentDto.class, name = "PATENTE"),
        @JsonSubTypes.Type(value = IndustrialDesignDto.class, name = "DESENHO_INDUSTRIAL"),
        @JsonSubTypes.Type(value = CultivarDto.class, name = "CULTIVAR"),
        @JsonSubTypes.Type(value = GeographicIndicationDto.class, name = "INDICACAO_GEOGRAFICA")
})
public abstract class IntellectualPropertyDto {
    private Long id;
    private String title;
    private String description;
    private IntellectualPropertyType type;
    private IntellectualPropertyStatus status;
    private LocalDate requestDate;
    private LocalDate grantDate;
    private LocalDate expirationDate;
    private Long inventorId;
    private String inventorName;
    private Long startupId;
    private String startupName;
    private String processingStage;
    private List<DocumentDto> documents;
}
