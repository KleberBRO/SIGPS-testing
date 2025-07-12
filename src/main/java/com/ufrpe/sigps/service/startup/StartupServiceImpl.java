// src/main/java/com/ufrpe/sigps/service/startup/StartupServiceImpl.java
package com.ufrpe.sigps.service.startup;

import com.ufrpe.sigps.dto.FileDto;

import com.ufrpe.sigps.dto.StartupDto;

import com.ufrpe.sigps.exception.CnpjAlreadyExistsException;
import com.ufrpe.sigps.model.FileStartup;

import com.ufrpe.sigps.model.Startup;

import com.ufrpe.sigps.repository.StartupRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ufrpe.sigps.service.S3StorageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupServiceImpl implements StartupService {

    private final StartupRepository startupRepository;
    private final S3StorageService s3StorageService;

    public StartupServiceImpl(StartupRepository startupRepository, S3StorageService s3StorageService) {
        this.startupRepository = startupRepository;
        this.s3StorageService = s3StorageService;
    }

    @Override
    @Transactional
    public StartupDto createStartup(StartupDto startupDto, List<MultipartFile> documentFiles, List<MultipartFile> imageFiles) {

        // --- ETAPA 1: VALIDAR PRIMEIRO ---
        // Verifica se o CNPJ já existe antes de fazer qualquer outra coisa.
        if (startupRepository.existsByCnpj(startupDto.getCnpj())) {
            throw new CnpjAlreadyExistsException("O CNPJ " + startupDto.getCnpj() + " já está cadastrado.");
        }

        List<String> uploadedFileUrls = new ArrayList<>();
        try {
            // --- ETAPA 2: FAZER UPLOAD DOS ARQUIVOS PARA O S3 ---
            // Processa documentos
            if (documentFiles != null && !documentFiles.isEmpty()) {
                for (MultipartFile file : documentFiles) {
                    String fileUrl = s3StorageService.uploadFile(file);
                    uploadedFileUrls.add(fileUrl); // Guarda a URL para o caso de precisar deletar

                    FileDto fileInfo = new FileDto();
                    fileInfo.setTitle(file.getOriginalFilename());
                    fileInfo.setFilePath(fileUrl);
                    fileInfo.setType("DOCUMENTO");
                    startupDto.addFile(fileInfo); // Usando um método helper no DTO
                }
            }
            // Processa imagens
            if (imageFiles != null && !imageFiles.isEmpty()) {
                for (MultipartFile file : imageFiles) {
                    String fileUrl = s3StorageService.uploadFile(file);
                    uploadedFileUrls.add(fileUrl); // Guarda a URL

                    FileDto imageInfo = new FileDto();
                    imageInfo.setTitle(file.getOriginalFilename());
                    imageInfo.setFilePath(fileUrl);
                    imageInfo.setType("IMAGEM");
                    startupDto.addFile(imageInfo);
                }
            }

            // --- ETAPA 3: SALVAR NO BANCO DE DADOS ---
            // Se todas as validações e uploads passaram, tentamos salvar no banco.
            Startup startup = toEntity(startupDto);
            Startup savedStartup = startupRepository.save(startup);
            return toDto(savedStartup);

        } catch (Exception e) {
            // --- ETAPA 4: LIMPEZA (ROLLBACK MANUAL DO S3) ---
            // Se qualquer exceção ocorrer DEPOIS dos uploads (ex: falha de conexão com o banco),
            // o @Transactional fará o rollback do banco, e este bloco limpará o S3.
            System.err.println("Ocorreu um erro ao salvar a startup. Iniciando rollback dos arquivos no S3...");
            for (String fileUrl : uploadedFileUrls) {
                try {
                    s3StorageService.deleteFile(fileUrl); // Você precisará criar este método no S3StorageService
                } catch (Exception s3e) {
                    System.err.println("Falha crítica: não foi possível deletar o arquivo " + fileUrl + " do S3 durante o rollback.");
                }
            }
            // Propaga a exceção original para que o cliente receba o erro 500
            throw new RuntimeException("Falha ao criar a startup. A operação foi desfeita.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StartupDto getStartupById(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + id + " não encontrada."));
        return toDto(startup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StartupDto> getAllStartups() {
        List<Startup> startups = startupRepository.findAll();
        return startups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StartupDto updateStartup(Long id, StartupDto startupDto) {
        return startupRepository.findById(id)
                .map(existingStartup -> {

                    existingStartup.setCnpj(startupDto.getCnpj());
                    existingStartup.setName(startupDto.getName());
                    existingStartup.setDescription(startupDto.getDescription());
                    existingStartup.setCreationDate(startupDto.getCreationDate());
                    existingStartup.setStatus(startupDto.getStatus());
                    existingStartup.setAcademicProjectLink(startupDto.getAcademicProjectLink());
                    existingStartup.setResearchGroupLink(startupDto.getResearchGroupLink());

                    Startup updatedStartup = startupRepository.save(existingStartup);
                    return toDto(updatedStartup);
                })
                .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + id + " não encontrada para atualização."));
    }

    @Override
    @Transactional
    public void deleteStartup(Long id) {
        if (!startupRepository.existsById(id)) {
            throw new EntityNotFoundException("Startup com ID " + id + " não encontrada para exclusão.");
        }
        startupRepository.deleteById(id);
    }

    private StartupDto toDto(Startup startup) {
        if (startup == null) {
            return null;
        }

        // Converte a lista de entidades de arquivo para uma lista de DTOs de arquivo
        List<FileDto> fileDtos = startup.getFiles() != null ? startup.getFiles().stream()
                .map(file -> FileDto.builder()
                        .id(file.getId())
                        .title(file.getTitle())
                        .type(file.getType())
                        .filePath(file.getFilePath())
                        .build())
                .collect(Collectors.toList()) : Collections.emptyList();

        return StartupDto.builder()
                .id(startup.getId())
                .cnpj(startup.getCnpj())
                .name(startup.getName())
                .description(startup.getDescription())
                .creationDate(startup.getCreationDate())
                .status(startup.getStatus())
                .academicProjectLink(startup.getAcademicProjectLink())
                .researchGroupLink(startup.getResearchGroupLink())
                .files(fileDtos) // <-- ADIÇÃO: Inclui a lista de arquivos no DTO de resposta
                .build();
    }

    private Startup toEntity(StartupDto startupDto) {
        if (startupDto == null) {
            return null;
        }

        // Constrói a entidade Startup com os campos principais
        Startup startup = Startup.builder()
                .id(startupDto.getId())
                .cnpj(startupDto.getCnpj())
                .name(startupDto.getName())
                .description(startupDto.getDescription())
                .creationDate(startupDto.getCreationDate())
                .status(startupDto.getStatus())
                .academicProjectLink(startupDto.getAcademicProjectLink())
                .researchGroupLink(startupDto.getResearchGroupLink())
                .build();

        // Converte a lista de DTOs de arquivo para uma lista de entidades de arquivo
        if (startupDto.getFiles() != null && !startupDto.getFiles().isEmpty()) {
            List<FileStartup> fileEntities = startupDto.getFiles().stream()
                    .map(fileDto -> {
                        FileStartup file = FileStartup.builder()
                                .id(fileDto.getId())
                                .title(fileDto.getTitle())
                                .type(fileDto.getType())
                                .filePath(fileDto.getFilePath())
                                .build();
                        file.setStartup(startup); // <-- ADIÇÃO CRÍTICA: Define a relação inversa
                        return file;
                    })
                    .collect(Collectors.toList());
            startup.setFiles(fileEntities); // <-- ADIÇÃO: Associa a lista de arquivos à startup
        }

        return startup;
    }
}