
// src/main/java/com/ufrpe/sigps/service/startup/StartupService.java
package com.ufrpe.sigps.service.startup;

import com.ufrpe.sigps.dto.StartupDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StartupService {

    StartupDto createStartup(StartupDto startupDto, List<MultipartFile> documentFiles, List<MultipartFile> imageFiles);

    StartupDto getStartupById(Long id);

    List<StartupDto> getAllStartups();

    StartupDto updateStartup(Long id, StartupDto startupDto);

    void deleteStartup(Long id);
}