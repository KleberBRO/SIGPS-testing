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

@Service // Marca esta classe como um componente de serviço do Spring
public class StartupServiceImpl implements StartupService {

    private final StartupRepository startupRepository;

    // Injeção de dependência do StartupRepository via construtor
    public StartupServiceImpl(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    @Override
    @Transactional // Garante que a operação seja atômica
    public StartupDto createStartup(StartupDto startupDto) {
        // Converte o DTO para Entidade
        Startup startup = toEntity(startupDto);
        // Salva a entidade no banco de dados
        Startup savedStartup = startupRepository.save(startup);
        // Converte a Entidade salva de volta para DTO e retorna
        return toDto(savedStartup);
    }

    @Override
    @Transactional(readOnly = true) // Otimização para leitura
    public StartupDto getStartupById(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Startup com ID " + id + " não encontrada."));
        return toDto(startup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StartupDto> getAllStartups() {
        List<Startup> startups = startupRepository.findAll();
        // Converte a lista de Entidades para uma lista de DTOs
        return startups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StartupDto updateStartup(Long id, StartupDto startupDto) {
        return startupRepository.findById(id)
                .map(existingStartup -> {
                    // Atualiza os campos da entidade existente com os dados do DTO
                    // Não alteramos o ID, pois ele já existe
                    existingStartup.setCnpj(startupDto.getCnpj());
                    existingStartup.setName(startupDto.getName());
                    existingStartup.setDescription(startupDto.getDescription());
                    existingStartup.setCreationDate(startupDto.getCreationDate());
                    existingStartup.setStatus(startupDto.getStatus());
                    existingStartup.setAcademicProjectLink(startupDto.getAcademicProjectLink());
                    existingStartup.setResearchGroupLink(startupDto.getResearchGroupLink());

                    // Salva a entidade atualizada
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

    /**
     * Métodos utilitários para conversão entre Entidade e DTO.
     * Podem ser movidos para uma classe Mapper dedicada em projetos maiores (ex: MapStruct).
     */
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
                .id(startupDto.getId()) // ID pode ser nulo para novas entidades (gerado pelo DB)
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