// src/main/java/com/ufrpe/sigps/service/pi/IntellectualPropertyService.java
package com.ufrpe.sigps.service.pi;

import com.ufrpe.sigps.dto.IntellectualPropertyDto; // Importe o seu DTO
import java.util.List;
import java.time.LocalDate;

public interface IntellectualPropertyService {

    IntellectualPropertyDto createIntellectualProperty(IntellectualPropertyDto piDto);
    IntellectualPropertyDto getIntellectualPropertyById(Long id);
    List<IntellectualPropertyDto> getIntellectualPropertyByTitle(String title);
    List<IntellectualPropertyDto> getIntellectualPropertyByInventorName (String inventorName);
    List<IntellectualPropertyDto> getIntellectualPropertyByRequestDate (LocalDate requestDate);
    List<IntellectualPropertyDto> getIntellectualPropertyByGrantDate (LocalDate grantDate);
    List<IntellectualPropertyDto> getIntellectualPropertyByExpirationDate (LocalDate expirationDate);
    List<IntellectualPropertyDto> getAllIntellectualProperties();
    IntellectualPropertyDto updateIntellectualProperty(Long id, IntellectualPropertyDto piDto);
    void deleteIntellectualProperty(Long id);

}