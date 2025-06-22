// src/main/java/com/ufrpe/sigps/service/pi/IntellectualPropertyService.java
package com.ufrpe.sigps.service.pi;

import com.ufrpe.sigps.dto.IntellectualPropertyDto; // Importe o seu DTO
import java.util.List;

public interface IntellectualPropertyService {

    IntellectualPropertyDto createIntellectualProperty(IntellectualPropertyDto piDto);
    IntellectualPropertyDto getIntellectualPropertyById(Long id);
    List<IntellectualPropertyDto> getAllIntellectualProperties();
    IntellectualPropertyDto updateIntellectualProperty(Long id, IntellectualPropertyDto piDto);
    void deleteIntellectualProperty(Long id);
    // Você pode adicionar mais métodos aqui conforme a necessidade
    // Ex: List<IntellectualPropertyDto> findByInventorId(Long inventorId);
}