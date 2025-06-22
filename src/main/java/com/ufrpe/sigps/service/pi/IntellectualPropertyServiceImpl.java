// src/main/java/com/ufrpe/sigps/service/pi/IntellectualPropertyServiceImpl.java
package com.ufrpe.sigps.service.pi;

import com.ufrpe.sigps.dto.*; // Importa todos os DTOs do pacote dto
import com.ufrpe.sigps.model.*; // Importa todas as entidades do pacote model

import com.ufrpe.sigps.repository.IntellectualPropertyRepository;
import com.ufrpe.sigps.repository.InventorRepository;
import com.ufrpe.sigps.repository.StartupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntellectualPropertyServiceImpl implements IntellectualPropertyService {

    private final IntellectualPropertyRepository intellectualPropertyRepository;
    private final InventorRepository inventorRepository;
    private final StartupRepository startupRepository;

    @Autowired
    public IntellectualPropertyServiceImpl(
            IntellectualPropertyRepository intellectualPropertyRepository,
            InventorRepository inventorRepository,
            StartupRepository startupRepository) {
        this.intellectualPropertyRepository = intellectualPropertyRepository;
        this.inventorRepository = inventorRepository;
        this.startupRepository = startupRepository;
    }

    @Override
    public IntellectualPropertyDto createIntellectualProperty(IntellectualPropertyDto piDto) {
        // Converte o DTO genérico (ou de subclasse) para a entidade correta
        IntellectualProperty pi = convertDtoToEntity(piDto);
        IntellectualProperty savedPI = intellectualPropertyRepository.save(pi);
        // Converte a entidade salva de volta para o DTO apropriado (subclasse de DTO)
        return convertEntityToDto(savedPI);
    }

    @Override
    public IntellectualPropertyDto getIntellectualPropertyById(Long id) {
        return intellectualPropertyRepository.findById(id)
                // A linha abaixo foi ajustada para garantir a inferência de tipo
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade Intelectual com ID " + id + " não encontrada."));
    }

    @Override
    public List<IntellectualPropertyDto> getAllIntellectualProperties() {
        List<IntellectualProperty> ips = intellectualPropertyRepository.findAll();
        return ips.stream()
                // A linha abaixo foi ajustada para garantir a inferência de tipo
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public IntellectualPropertyDto updateIntellectualProperty(Long id, IntellectualPropertyDto piDto) {
        return intellectualPropertyRepository.findById(id)
                .map(existingPI -> {
                    // Verificação de tipo: Garantir que o tipo do DTO corresponde ao tipo da entidade existente
                    if (!existingPI.getType().equals(piDto.getType())) {
                        throw new IllegalArgumentException("Não é permitido mudar o tipo de uma Propriedade Intelectual existente.");
                    }

                    // Atualiza campos da classe base (IntellectualProperty)
                    existingPI.setTitle(piDto.getTitle());
                    existingPI.setDescription(piDto.getDescription());
                    existingPI.setStatus(piDto.getStatus());
                    existingPI.setRequestDate(piDto.getRequestDate());
                    existingPI.setGrantDate(piDto.getGrantDate());
                    existingPI.setExpirationDate(piDto.getExpirationDate());
                    existingPI.setProcessingStage(piDto.getProcessingStage());

                    // Lógica para atualização de campos específicos da subclasse
                    // Isso exige um 'instanceof' e um cast para cada tipo específico
                    switch (piDto.getType()) {
                        case SOFTWARE -> {
                            if (existingPI instanceof Software existingSoftware) {
                                SoftwareDto softwareDto = (SoftwareDto) piDto; // Cast seguro após verificação de tipo

                                existingSoftware.setHolderName(softwareDto.getHolderName());
                                existingSoftware.setHolderAddress(softwareDto.getHolderAddress());
                                existingSoftware.setHolderCpfCnpj(softwareDto.getHolderCpfCnpj());
                                existingSoftware.setAuthorsData(softwareDto.getAuthorsData());
                                existingSoftware.setCreationDate(softwareDto.getCreationDate());
                                existingSoftware.setPublicationDate(softwareDto.getPublicationDate());
                                existingSoftware.setProgrammingLanguage(softwareDto.getProgrammingLanguage());
                                existingSoftware.setApplicationField(softwareDto.getApplicationField());
                                existingSoftware.setProgramType(softwareDto.getProgramType());
                                existingSoftware.setAlgorithm(softwareDto.getAlgorithm());
                                existingSoftware.setHashDescription(softwareDto.getHashDescription());
                                existingSoftware.setAuthorizedDerivation(softwareDto.getAuthorizedDerivation());
                                existingSoftware.setSourceCodePath(softwareDto.getSourceCodePath());
                            }
                        }
                        case MARCA -> {
                            if (existingPI instanceof Brand existingBrand) {
                                BrandDto brandDto = (BrandDto) piDto;

                                existingBrand.setBrandType(brandDto.getBrandType());
                                existingBrand.setBrandName(brandDto.getBrandName());
                                existingBrand.setImageUrl(brandDto.getImageUrl());
                                existingBrand.setNiceClassificationCode(brandDto.getNiceClassificationCode());
                                existingBrand.setActivityDescription(brandDto.getActivityDescription());
                                existingBrand.setRequestNature(brandDto.getRequestNature());
                                existingBrand.setUsageStatus(brandDto.getUsageStatus());
                                existingBrand.setStartUsageDate(brandDto.getStartUsageDate());
                            }
                        }
                        case CULTIVAR -> {
                            if (existingPI instanceof Cultivar existingCultivar) {
                                CultivarDto cultivarDto = (CultivarDto) piDto;

                                existingCultivar.setCultivarName(cultivarDto.getCultivarName());
                                existingCultivar.setBotanicalSpecies(cultivarDto.getBotanicalSpecies());
                                existingCultivar.setCommercialDenomination(cultivarDto.getCommercialDenomination());
                                existingCultivar.setOrigin(cultivarDto.getOrigin());
                                existingCultivar.setDistinctiveCharacteristics(cultivarDto.getDistinctiveCharacteristics());
                                existingCultivar.setPurposeOfUse(cultivarDto.getPurposeOfUse());
                                existingCultivar.setCountryOfOrigin(cultivarDto.getCountryOfOrigin());
                                existingCultivar.setProtectionType(cultivarDto.getProtectionType());
                                existingCultivar.setCreationDevelopmentDate(cultivarDto.getCreationDevelopmentDate());
                                existingCultivar.setDheData(cultivarDto.getDheData());
                            }
                        }
                        case INDICACAO_GEOGRAFICA -> {
                            if (existingPI instanceof GeographicIndication existingGI) {
                                GeographicIndicationDto giDto = (GeographicIndicationDto) piDto;

                                existingGI.setGeographicName(giDto.getGeographicName());
                                existingGI.setProductOrService(giDto.getProductOrService());
                                existingGI.setAreaDelimitation(giDto.getAreaDelimitation());
                                existingGI.setIgNature(giDto.getIgNature());
                                existingGI.setVisualRepresentationUrl(giDto.getVisualRepresentationUrl());
                            }
                        }
                        case DESENHO_INDUSTRIAL -> {
                            if (existingPI instanceof IndustrialDesign existingID) {
                                IndustrialDesignDto idDto = (IndustrialDesignDto) piDto;

                                existingID.setLocarnoClassification(idDto.getLocarnoClassification());
                                existingID.setNumberOfVariations(idDto.getNumberOfVariations());
                                existingID.setDesignCreationDate(idDto.getDesignCreationDate());
                                existingID.setApplicationField(idDto.getApplicationField());
                                existingID.setUnionistPriority(idDto.getUnionistPriority());
                            }
                        }
                        case PATENTE -> {
                            if (existingPI instanceof Patent existingPatent) {
                                PatentDto patentDto = (PatentDto) piDto;

                                existingPatent.setPatentType(patentDto.getPatentType());
                                existingPatent.setInternationalClassification(patentDto.getInternationalClassification());
                                existingPatent.setTechnicalApplicationField(patentDto.getTechnicalApplicationField());
                                existingPatent.setFilingDate(patentDto.getFilingDate());
                                existingPatent.setPriorityDate(patentDto.getPriorityDate());
                                existingPatent.setPriorityCountry(patentDto.getPriorityCountry());
                                existingPatent.setPriorityNumber(patentDto.getPriorityNumber());
                                existingPatent.setPreviousRequestRelated(patentDto.getPreviousRequestRelated());
                            }
                        }
                        default -> {
                        }
                        // Não há campos específicos para outros tipos ou um tipo desconhecido
                    }

                    // Atualização de relacionamentos (Inventor e Startup)
                    if (piDto.getInventorId() != null) {
                        Inventor inventor = inventorRepository.findById(piDto.getInventorId())
                                .orElseThrow(() -> new EntityNotFoundException("Inventor com ID " + piDto.getInventorId() + " não encontrado."));
                        existingPI.setInventor(inventor);
                    } else {
                        // Se inventorId for null no DTO, mas a PI precisa de um inventor, pode ser um erro
                        throw new IllegalArgumentException("O inventor é obrigatório para Propriedade Intelectual.");
                    }

                    if (piDto.getStartupId() != null) {
                        Startup startup = startupRepository.findById(piDto.getStartupId())
                                .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + piDto.getStartupId() + " não encontrada."));
                        existingPI.setStartup(startup);
                    } else {
                        existingPI.setStartup(null); // Se o ID da startup for nulo no DTO, desvincula
                    }

                    // ATENÇÃO: A atualização de coleções (como 'documents') é mais complexa
                    // e geralmente exige uma lógica separada para adicionar/remover/atualizar itens.
                    // Para este exemplo, a lista de documentos NÃO ESTÁ sendo atualizada diretamente
                    // através do DTO da PI para evitar complexidade indevida aqui.
                    // Se precisar gerenciar documentos junto com a PI, recomendo um serviço de documento
                    // ou métodos dedicados para adicionar/remover documentos.

                    IntellectualProperty updatedPI = intellectualPropertyRepository.save(existingPI);
                    // A linha abaixo foi ajustada para garantir a inferência de tipo
                    return convertEntityToDto(updatedPI);
                })
                .orElseThrow(() -> new EntityNotFoundException("Propriedade Intelectual com ID " + id + " não encontrada para atualização."));
    }

    @Override
    public void deleteIntellectualProperty(Long id) {
        if (!intellectualPropertyRepository.existsById(id)) {
            throw new EntityNotFoundException("Propriedade Intelectual com ID " + id + " não encontrada para exclusão.");
        }
        intellectualPropertyRepository.deleteById(id);
    }

    /**
     * Converte um DTO de Propriedade Intelectual para a entidade JPA correspondente.
     * Este método é crucial para lidar com a hierarquia de herança.
     *
     * @param dto O DTO de entrada (pode ser IntellectualPropertyDto ou suas subclasses).
     * @return A entidade concreta de Propriedade Intelectual.
     * @throws IllegalArgumentException se o tipo de PI for inválido ou ausente.
     * @throws EntityNotFoundException se o Inventor ou Startup referenciados não forem encontrados.
     */
    private IntellectualProperty convertDtoToEntity(IntellectualPropertyDto dto) {
        // Busca as entidades relacionadas (Inventor, Startup)
        Inventor inventor = inventorRepository.findById(dto.getInventorId())
                .orElseThrow(() -> new EntityNotFoundException("Inventor com ID " + dto.getInventorId() + " não encontrado."));

        Startup startup = null;
        if (dto.getStartupId() != null) {
            startup = startupRepository.findById(dto.getStartupId())
                    .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + dto.getStartupId() + " não encontrada."));
        }

        // Converte a lista de DocumentDto para Document (entidade)
        // Atenção: Documentos precisam ter o campo 'intellectualProperty' setado APÓS a criação da PI
        // para manter a relação bidirecional. Por isso, a lista é construída aqui,
        // mas a associação inversa será feita depois.
        List<Document> documents = null;
        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {
            documents = dto.getDocuments().stream()
                    .map(docDto -> Document.builder()
                            .id(docDto.getId()) // Pode ser nulo para novos documentos
                            .title(docDto.getTitle())
                            .type(docDto.getType())
                            .filePath(docDto.getFilePath())
                            // .intellectualProperty(null) // Será setado após a criação da PI
                            // .startup(null) // Se o documento não for de startup, mantém nulo
                            .build())
                    .collect(Collectors.toList());
        }

        IntellectualProperty intellectualProperty;

        // --- AQUI É O PONTO CHAVE: Instanciando a subclasse concreta baseada no tipo ---
        if (dto.getType() == null) {
            throw new IllegalArgumentException("O tipo da Propriedade Intelectual (pi_type) é obrigatório.");
        }

        switch (dto.getType()) {
            case SOFTWARE -> {
                SoftwareDto softwareDto = (SoftwareDto) dto; // Cast para o DTO de subclasse
                intellectualProperty = Software.builder()
                        .holderName(softwareDto.getHolderName())
                        .holderAddress(softwareDto.getHolderAddress())
                        .holderCpfCnpj(softwareDto.getHolderCpfCnpj())
                        .authorsData(softwareDto.getAuthorsData())
                        .creationDate(softwareDto.getCreationDate())
                        .publicationDate(softwareDto.getPublicationDate())
                        .programmingLanguage(softwareDto.getProgrammingLanguage())
                        .applicationField(softwareDto.getApplicationField())
                        .programType(softwareDto.getProgramType())
                        .algorithm(softwareDto.getAlgorithm())
                        .hashDescription(softwareDto.getHashDescription())
                        .authorizedDerivation(softwareDto.getAuthorizedDerivation())
                        .sourceCodePath(softwareDto.getSourceCodePath())
                        .build();
            }
            case MARCA -> {
                BrandDto brandDto = (BrandDto) dto;
                intellectualProperty = Brand.builder()
                        .brandType(brandDto.getBrandType())
                        .brandName(brandDto.getBrandName())
                        .imageUrl(brandDto.getImageUrl())
                        .niceClassificationCode(brandDto.getNiceClassificationCode())
                        .activityDescription(brandDto.getActivityDescription())
                        .requestNature(brandDto.getRequestNature())
                        .usageStatus(brandDto.getUsageStatus())
                        .startUsageDate(brandDto.getStartUsageDate())
                        .build();
            }
            case CULTIVAR -> {
                CultivarDto cultivarDto = (CultivarDto) dto;
                intellectualProperty = Cultivar.builder()
                        .cultivarName(cultivarDto.getCultivarName())
                        .botanicalSpecies(cultivarDto.getBotanicalSpecies())
                        .commercialDenomination(cultivarDto.getCommercialDenomination())
                        .origin(cultivarDto.getOrigin())
                        .distinctiveCharacteristics(cultivarDto.getDistinctiveCharacteristics())
                        .purposeOfUse(cultivarDto.getPurposeOfUse())
                        .countryOfOrigin(cultivarDto.getCountryOfOrigin())
                        .protectionType(cultivarDto.getProtectionType())
                        .creationDevelopmentDate(cultivarDto.getCreationDevelopmentDate())
                        .dheData(cultivarDto.getDheData())
                        .build();
            }
            case INDICACAO_GEOGRAFICA -> {
                GeographicIndicationDto giDto = (GeographicIndicationDto) dto;
                intellectualProperty = GeographicIndication.builder()
                        .geographicName(giDto.getGeographicName())
                        .productOrService(giDto.getProductOrService())
                        .areaDelimitation(giDto.getAreaDelimitation())
                        .igNature(giDto.getIgNature())
                        .visualRepresentationUrl(giDto.getVisualRepresentationUrl())
                        .build();
            }
            case DESENHO_INDUSTRIAL -> {
                IndustrialDesignDto idDto = (IndustrialDesignDto) dto;
                intellectualProperty = IndustrialDesign.builder()
                        .locarnoClassification(idDto.getLocarnoClassification())
                        .numberOfVariations(idDto.getNumberOfVariations())
                        .designCreationDate(idDto.getDesignCreationDate())
                        .applicationField(idDto.getApplicationField())
                        .unionistPriority(idDto.getUnionistPriority())
                        .build();
            }
            case PATENTE -> {
                PatentDto patentDto = (PatentDto) dto;
                intellectualProperty = Patent.builder()
                        .patentType(patentDto.getPatentType())
                        .internationalClassification(patentDto.getInternationalClassification())
                        .technicalApplicationField(patentDto.getTechnicalApplicationField())
                        .filingDate(patentDto.getFilingDate())
                        .priorityDate(patentDto.getPriorityDate())
                        .priorityCountry(patentDto.getPriorityCountry())
                        .priorityNumber(patentDto.getPriorityNumber())
                        .previousRequestRelated(patentDto.getPreviousRequestRelated())
                        .build();
            }
            default ->
                    throw new IllegalArgumentException("Tipo de Propriedade Intelectual ('" + dto.getType() + "') não reconhecido ou inválido.");
        }

        // --- Copia os campos da classe base (IntellectualPropertyDto) para a entidade construída ---
        intellectualProperty.setTitle(dto.getTitle());
        intellectualProperty.setDescription(dto.getDescription());
        intellectualProperty.setType(dto.getType()); // Tipo já foi usado para o switch, mas bom setar
        intellectualProperty.setStatus(dto.getStatus());
        intellectualProperty.setRequestDate(dto.getRequestDate());
        intellectualProperty.setGrantDate(dto.getGrantDate());
        intellectualProperty.setExpirationDate(dto.getExpirationDate());
        intellectualProperty.setProcessingStage(dto.getProcessingStage());
        intellectualProperty.setInventor(inventor);
        intellectualProperty.setStartup(startup);

        // Associa a PI aos documentos. Isso é crucial para o @OneToMany bidirecional
        if (documents != null) {
            for (Document doc : documents) {
                doc.setIntellectualProperty(intellectualProperty);
            }
        }
        intellectualProperty.setDocuments(documents); // Adiciona os documentos à PI

        return intellectualProperty;
    }

    /**
     * Converte uma entidade JPA de Propriedade Intelectual para o DTO de subclasse correspondente.
     * Este método é crucial para lidar com a hierarquia de herança na saída.
     *
     * @param entity A entidade de Propriedade Intelectual.
     * @return O DTO de subclasse de Propriedade Intelectual.
     */
    private IntellectualPropertyDto convertEntityToDto(IntellectualProperty entity) {
        List<DocumentDto> documentDtos = null;
        if (entity.getDocuments() != null && !entity.getDocuments().isEmpty()) {
            documentDtos = entity.getDocuments().stream()
                    .map(docEntity -> DocumentDto.builder()
                            .id(docEntity.getId())
                            .title(docEntity.getTitle())
                            .type(docEntity.getType())
                            .filePath(docEntity.getFilePath())
                            .build())
                    .collect(Collectors.toList());
        }

        IntellectualPropertyDto dto;

        // Instancia o DTO da subclasse correta e copia os campos específicos
        switch (entity.getType()) {
            case SOFTWARE -> {
                Software softwareEntity = (Software) entity; // Cast seguro
                dto = SoftwareDto.builder()
                        .holderName(softwareEntity.getHolderName())
                        .holderAddress(softwareEntity.getHolderAddress())
                        .holderCpfCnpj(softwareEntity.getHolderCpfCnpj())
                        .authorsData(softwareEntity.getAuthorsData())
                        .creationDate(softwareEntity.getCreationDate())
                        .publicationDate(softwareEntity.getPublicationDate())
                        .programmingLanguage(softwareEntity.getProgrammingLanguage())
                        .applicationField(softwareEntity.getApplicationField())
                        .programType(softwareEntity.getProgramType())
                        .algorithm(softwareEntity.getAlgorithm())
                        .hashDescription(softwareEntity.getHashDescription())
                        .authorizedDerivation(softwareEntity.getAuthorizedDerivation())
                        .sourceCodePath(softwareEntity.getSourceCodePath())
                        .build();
            }
            case MARCA -> {
                Brand brandEntity = (Brand) entity;
                dto = BrandDto.builder()
                        .brandType(brandEntity.getBrandType())
                        .brandName(brandEntity.getBrandName())
                        .imageUrl(brandEntity.getImageUrl())
                        .niceClassificationCode(brandEntity.getNiceClassificationCode())
                        .activityDescription(brandEntity.getActivityDescription())
                        .requestNature(brandEntity.getRequestNature())
                        .usageStatus(brandEntity.getUsageStatus())
                        .startUsageDate(brandEntity.getStartUsageDate())
                        .build();
            }
            case CULTIVAR -> {
                Cultivar cultivarEntity = (Cultivar) entity;
                dto = CultivarDto.builder()
                        .cultivarName(cultivarEntity.getCultivarName())
                        .botanicalSpecies(cultivarEntity.getBotanicalSpecies())
                        .commercialDenomination(cultivarEntity.getCommercialDenomination())
                        .origin(cultivarEntity.getOrigin())
                        .distinctiveCharacteristics(cultivarEntity.getDistinctiveCharacteristics())
                        .purposeOfUse(cultivarEntity.getPurposeOfUse())
                        .countryOfOrigin(cultivarEntity.getCountryOfOrigin())
                        .protectionType(cultivarEntity.getProtectionType())
                        .creationDevelopmentDate(cultivarEntity.getCreationDevelopmentDate())
                        .dheData(cultivarEntity.getDheData())
                        .build();
            }
            case INDICACAO_GEOGRAFICA -> {
                GeographicIndication giEntity = (GeographicIndication) entity;
                dto = GeographicIndicationDto.builder()
                        .geographicName(giEntity.getGeographicName())
                        .productOrService(giEntity.getProductOrService())
                        .areaDelimitation(giEntity.getAreaDelimitation())
                        .igNature(giEntity.getIgNature())
                        .visualRepresentationUrl(giEntity.getVisualRepresentationUrl())
                        .build();
            }
            case DESENHO_INDUSTRIAL -> {
                IndustrialDesign idEntity = (IndustrialDesign) entity;
                dto = IndustrialDesignDto.builder()
                        .locarnoClassification(idEntity.getLocarnoClassification())
                        .numberOfVariations(idEntity.getNumberOfVariations())
                        .designCreationDate(idEntity.getDesignCreationDate())
                        .applicationField(idEntity.getApplicationField())
                        .unionistPriority(idEntity.getUnionistPriority())
                        .build();
            }
            case PATENTE -> {
                Patent patentEntity = (Patent) entity;
                dto = PatentDto.builder()
                        .patentType(patentEntity.getPatentType())
                        .internationalClassification(patentEntity.getInternationalClassification())
                        .technicalApplicationField(patentEntity.getTechnicalApplicationField())
                        .filingDate(patentEntity.getFilingDate())
                        .priorityDate(patentEntity.getPriorityDate())
                        .priorityCountry(patentEntity.getPriorityCountry())
                        .priorityNumber(patentEntity.getPriorityNumber())
                        .previousRequestRelated(patentEntity.getPreviousRequestRelated())
                        .build();
            }
            default ->
                    throw new IllegalStateException("Tipo de Propriedade Intelectual desconhecido ou inválido para conversão de entidade para DTO: " + entity.getType());
        }

        // --- Copia os campos da classe base (IntellectualProperty) para o DTO de subclasse ---
        // O SuperBuilder dos DTOs concretos já inclui os campos da base.
        // É possível usar os setters se os campos não foram preenchidos pelo builder da subclasse.
        // No entanto, como @SuperBuilder funciona aqui, a maioria já deve ter sido copiada.
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setRequestDate(entity.getRequestDate());
        dto.setGrantDate(entity.getGrantDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setProcessingStage(entity.getProcessingStage());
        dto.setInventorId(entity.getInventor() != null ? entity.getInventor().getId() : null);
        dto.setInventorName(entity.getInventor() != null ? entity.getInventor().getName() : null); // Assumindo Inventor tem 'getName()'
        dto.setStartupId(entity.getStartup() != null ? entity.getStartup().getId() : null);
        dto.setStartupName(entity.getStartup() != null ? entity.getStartup().getName() : null); // Assumindo Startup tem 'getName()'
        dto.setDocuments(documentDtos);

        return dto;
    }
}