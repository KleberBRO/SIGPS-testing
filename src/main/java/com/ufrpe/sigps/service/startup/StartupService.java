
// src/main/java/com/ufrpe/sigps/service/startup/StartupService.java
package com.ufrpe.sigps.service.startup;

import com.ufrpe.sigps.dto.StartupDto;
import java.util.List;

public interface StartupService {

    /**
     * Cria uma nova Startup a partir de um DTO.
     * @param startupDto O DTO da Startup a ser criada.
     * @return O DTO da Startup criada, com o ID gerado.
     */
    StartupDto createStartup(StartupDto startupDto);

    /**
     * Busca uma Startup pelo seu ID.
     * @param id O ID da Startup a ser buscada.
     * @return O DTO da Startup encontrada.
     * @throws jakarta.persistence.EntityNotFoundException se a Startup não for encontrada.
     */
    StartupDto getStartupById(Long id);

    /**
     * Lista todas as Startups cadastradas.
     * @return Uma lista de DTOs de todas as Startups.
     */
    List<StartupDto> getAllStartups();

    /**
     * Atualiza uma Startup existente.
     * @param id O ID da Startup a ser atualizada.
     * @param startupDto O DTO com os dados atualizados da Startup.
     * @return O DTO da Startup atualizada.
     * @throws jakarta.persistence.EntityNotFoundException se a Startup não for encontrada.
     */
    StartupDto updateStartup(Long id, StartupDto startupDto);

    /**
     * Deleta uma Startup pelo seu ID.
     * @param id O ID da Startup a ser deletada.
     * @throws jakarta.persistence.EntityNotFoundException se a Startup não for encontrada.
     */
    void deleteStartup(Long id);
}