// src/main/java/com/ufrpe/sigps/service/pi/IntellectualPropertyServiceImpl.java
package com.ufrpe.sigps.service.pi;

import com.ufrpe.sigps.dto.*; // Importa todos os DTOs do pacote dto
import com.ufrpe.sigps.model.*; // Importa todas as entidades do pacote model
import org.springframework.web.multipart.MultipartFile;
import com.ufrpe.sigps.service.S3StorageService;

import com.ufrpe.sigps.repository.IntellectualPropertyRepository;
import com.ufrpe.sigps.repository.InventorRepository;
import com.ufrpe.sigps.repository.StartupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntellectualPropertyServiceImpl implements IntellectualPropertyService {

    private final IntellectualPropertyRepository intellectualPropertyRepository;
    private final InventorRepository inventorRepository;
    private final StartupRepository startupRepository;
    private final S3StorageService s3StorageService;

    @Autowired
    public IntellectualPropertyServiceImpl(
            IntellectualPropertyRepository intellectualPropertyRepository,
            InventorRepository inventorRepository,
            StartupRepository startupRepository,
            S3StorageService s3StorageService) {
            this.intellectualPropertyRepository = intellectualPropertyRepository;
            this.inventorRepository = inventorRepository;
            this.startupRepository = startupRepository;
            this.s3StorageService = s3StorageService;
    }

    @Override
    public IntellectualPropertyDto createIntellectualProperty(IntellectualPropertyDto piDto, List<MultipartFile> documentFiles, List<MultipartFile> imageFiles) {
        if (piDto.getFiles() == null) {
            piDto.setFiles(new ArrayList<>());
        }

        // Processa documentos
        if (documentFiles != null && !documentFiles.isEmpty()) {
            for (MultipartFile file : documentFiles) {
                String fileUrl = s3StorageService.uploadFile(file); // Faz upload para S3
                FileDto fileInfo = new FileDto();
                fileInfo.setTitle(file.getOriginalFilename());
                fileInfo.setFilePath(fileUrl); // Salva a URL completa do S3
                fileInfo.setType("DOCUMENTO");
                piDto.getFiles().add(fileInfo);
            }
        }

        // Processa imagens
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                String fileUrl = s3StorageService.uploadFile(file); // Faz upload para S3
                FileDto imageInfo = new FileDto();
                imageInfo.setTitle(file.getOriginalFilename());
                imageInfo.setFilePath(fileUrl); // Salva a URL completa do S3
                imageInfo.setType("IMAGEM");
                piDto.getFiles().add(imageInfo);
            }
        }

        return createIntellectualProperty(piDto); // Chama o método original
    }


    @Override
    public IntellectualPropertyDto createIntellectualProperty(IntellectualPropertyDto piDto) {
        IntellectualProperty pi = convertDtoToEntity(piDto);
        IntellectualProperty savedPI = intellectualPropertyRepository.save(pi);
        return convertEntityToDto(savedPI);
    }

    @Override
    public IntellectualPropertyDto getIntellectualPropertyById(Long id) {
        return intellectualPropertyRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade Intelectual com ID " + id + " não encontrada."));
    }

    @Override
    public List<IntellectualPropertyDto> getIntellectualPropertyByTitle(String title) {
        return intellectualPropertyRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IntellectualPropertyDto> getIntellectualPropertyByInventorName(String inventorName) {
        return intellectualPropertyRepository.findByInventorName(inventorName).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IntellectualPropertyDto> getIntellectualPropertyByRequestDate(LocalDate requestDate) {
        return intellectualPropertyRepository.findByRequestDate(requestDate).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IntellectualPropertyDto> getIntellectualPropertyByGrantDate(LocalDate grantDate) {
        return intellectualPropertyRepository.findByGrantDate(grantDate).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IntellectualPropertyDto> getIntellectualPropertyByExpirationDate(LocalDate expirationDate) {
        return intellectualPropertyRepository.findByExpirationDate(expirationDate).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<IntellectualPropertyDto> getAllIntellectualProperties() {
        List<IntellectualProperty> ips = intellectualPropertyRepository.findAll();
        return ips.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public IntellectualPropertyDto updateIntellectualProperty(Long id, IntellectualPropertyDto piDto) {
        return intellectualPropertyRepository.findById(id)
                .map(existingPI -> {
                    if (!existingPI.getType().equals(piDto.getType())) {
                        throw new IllegalArgumentException("Não é permitido mudar o tipo de uma Propriedade Intelectual existente.");
                    }
                    existingPI.setTitle(piDto.getTitle());
                    existingPI.setDescription(piDto.getDescription());
                    existingPI.setStatus(piDto.getStatus());
                    existingPI.setRequestDate(piDto.getRequestDate());
                    existingPI.setGrantDate(piDto.getGrantDate());
                    existingPI.setExpirationDate(piDto.getExpirationDate());
                    existingPI.setProcessingStage(piDto.getProcessingStage());

                    switch (piDto.getType()) {
                        case SOFTWARE -> {
                            if (existingPI instanceof Software existingSoftware) {
                                SoftwareDto softwareDto = (SoftwareDto) piDto;

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
                                existingGI.setProduct(giDto.getProduct());
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
                    }


                    if (piDto.getInventorId() != null) {
                        Inventor inventor = inventorRepository.findById(piDto.getInventorId())
                                .orElseThrow(() -> new EntityNotFoundException("Inventor com ID " + piDto.getInventorId() + " não encontrado."));
                        existingPI.setInventor(inventor);
                    } else {

                        throw new IllegalArgumentException("O inventor é obrigatório para Propriedade Intelectual.");
                    }

                    if (piDto.getStartupId() != null) {
                        Startup startup = startupRepository.findById(piDto.getStartupId())
                                .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + piDto.getStartupId() + " não encontrada."));
                        existingPI.setStartup(startup);
                    } else {
                        existingPI.setStartup(null);
                    }

                    IntellectualProperty updatedPI = intellectualPropertyRepository.save(existingPI);
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

    private IntellectualProperty convertDtoToEntity(IntellectualPropertyDto dto) {

        Inventor inventor = inventorRepository.findById(dto.getInventorId())
                .orElseThrow(() -> new EntityNotFoundException("Inventor com ID " + dto.getInventorId() + " não encontrado."));

        Startup startup = null;
        if (dto.getStartupId() != null) {
            startup = startupRepository.findById(dto.getStartupId())
                    .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + dto.getStartupId() + " não encontrada."));
        }

        List<FileApp> files = null;
        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            files = dto.getFiles().stream()
                    .map(docDto -> FileApp.builder()
                            .id(docDto.getId())
                            .title(docDto.getTitle())
                            .type(docDto.getType())
                            .filePath(docDto.getFilePath())
                            // .intellectualProperty(null) - Será setado após a criação da PI
                            // .startup(null) - Se o documento não for de startup, mantém nulo
                            .build())
                    .collect(Collectors.toList());
        }

        IntellectualProperty intellectualProperty;

        if (dto.getType() == null) {
            throw new IllegalArgumentException("O tipo da Propriedade Intelectual (pi_type) é obrigatório.");
        }
        switch (dto.getType()) {
            case SOFTWARE -> {
                SoftwareDto softwareDto = (SoftwareDto) dto;
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
                        .product(giDto.getProduct())
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

        intellectualProperty.setId(dto.getId());
        intellectualProperty.setTitle(dto.getTitle());
        intellectualProperty.setDescription(dto.getDescription());
        intellectualProperty.setType(dto.getType());
        intellectualProperty.setStatus(dto.getStatus());
        intellectualProperty.setRequestDate(dto.getRequestDate());
        intellectualProperty.setGrantDate(dto.getGrantDate());
        intellectualProperty.setExpirationDate(dto.getExpirationDate());
        intellectualProperty.setProcessingStage(dto.getProcessingStage());
        intellectualProperty.setInventor(inventor);
        intellectualProperty.setStartup(startup);

        if (files != null) {
            for (FileApp doc : files) {
                doc.setIntellectualProperty(intellectualProperty);
            }
        }
        intellectualProperty.setFiles(files);

        return intellectualProperty;
    }

    private IntellectualPropertyDto convertEntityToDto(IntellectualProperty entity) {
        List<FileDto> fileDtos = null;
        if (entity.getFiles() != null && !entity.getFiles().isEmpty()) {
            fileDtos = entity.getFiles().stream()
                    .map(docEntity -> FileDto.builder()
                            .id(docEntity.getId())
                            .title(docEntity.getTitle())
                            .type(docEntity.getType())
                            .filePath(docEntity.getFilePath())
                            .build())
                    .collect(Collectors.toList());
        }

        IntellectualPropertyDto dto;

        switch (entity.getType()) {
            case SOFTWARE -> {
                Software softwareEntity = (Software) entity;
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
                        .product(giEntity.getProduct())
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
        dto.setInventorName(entity.getInventor() != null ? entity.getInventor().getName() : null);
        dto.setStartupId(entity.getStartup() != null ? entity.getStartup().getId() : null);
        dto.setStartupName(entity.getStartup() != null ? entity.getStartup().getName() : null);
        dto.setFiles(fileDtos);

        return dto;
    }
}