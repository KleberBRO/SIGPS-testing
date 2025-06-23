package com.ufrpe.sigps.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "geographic_indication")
@DiscriminatorValue("INDICACAO_GEOGRAFICA")
public class GeographicIndication extends IntellectualProperty {

    @Column(name = "geographic_name", nullable = false)
    private String geographicName;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "area_delimitation", columnDefinition = "TEXT", nullable = false)
    private String areaDelimitation;

    @Column(name = "ig_nature", nullable = false) // Produto ou serviço
    private String igNature;

    @Column(name = "visual_representation_url") // Caminho para a imagem
    private String visualRepresentationUrl;

    // Documentos como caderno de especificações, procuração, estatuto social, atas, declaração de produtores, comprovantes.
    // Estes serão tratados como uma lista de 'Document' relacionados à PI, como já previsto na classe IntellectualProperty.
}
