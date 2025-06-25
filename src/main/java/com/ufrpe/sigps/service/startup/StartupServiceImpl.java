// src/main/java/com/ufrpe/sigps/service/startup/StartupServiceImpl.java
package com.ufrpe.sigps.service.startup;

import com.ufrpe.sigps.dto.StartupDto;
import com.ufrpe.sigps.model.Startup;
import com.ufrpe.sigps.repository.StartupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupServiceImpl implements StartupService {

    private final StartupRepository startupRepository;

    public StartupServiceImpl(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    @Override
    @Transactional
    public StartupDto createStartup(StartupDto startupDto) {

        Startup startup = toEntity(startupDto);
        Startup savedStartup = startupRepository.save(startup);
        return toDto(savedStartup);
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
        return StartupDto.builder()
                .id(startup.getId())
                .cnpj(startup.getCnpj())
                .name(startup.getName())
                .description(startup.getDescription())
                .creationDate(startup.getCreationDate())
                .status(startup.getStatus())
                .academicProjectLink(startup.getAcademicProjectLink())
                .researchGroupLink(startup.getResearchGroupLink())
                .build();
    }

    private Startup toEntity(StartupDto startupDto) {
        if (startupDto == null) {
            return null;
        }
        return Startup.builder()
                .id(startupDto.getId())
                .cnpj(startupDto.getCnpj())
                .name(startupDto.getName())
                .description(startupDto.getDescription())
                .creationDate(startupDto.getCreationDate())
                .status(startupDto.getStatus())
                .academicProjectLink(startupDto.getAcademicProjectLink())
                .researchGroupLink(startupDto.getResearchGroupLink())
                .build();
    }
}