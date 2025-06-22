// src/main/java/com/ufrpe/sigps/controller/pi/IntellectualPropertyController.java
package com.ufrpe.sigps.controller.pi;

import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import com.ufrpe.sigps.service.pi.IntellectualPropertyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intellectual-properties")
public class IntellectualPropertyController {

    private final IntellectualPropertyService intellectualPropertyService;

    public IntellectualPropertyController(IntellectualPropertyService intellectualPropertyService) {
        this.intellectualPropertyService = intellectualPropertyService;
    }

    /**
     * Endpoint para criar uma nova Propriedade Intelectual.
     * Recebe um DTO genérico (ou de subclasse) e o processa.
     * POST /api/intellectual-properties*
     * Exemplo de JSON para SoftwareDto:
     * {
     * "title": "Sistema de Gestão Acadêmica",
     * "description": "Software para gerenciamento de cursos e alunos.",
     * "type": "SOFTWARE",
     * "status": "EM_ANALISE",
     * "requestDate": "2023-01-15",
     * "processingStage": "FASE_INICIAL",
     * "inventorId": 1,
     * "holderName": "UFRPE",
     * "creationDate": "2022-05-01",
     * "programmingLanguage": "Java"
     * }
     */
    @PostMapping
    public ResponseEntity<IntellectualPropertyDto> createIntellectualProperty(@Valid @RequestBody IntellectualPropertyDto piDto) {
        IntellectualPropertyDto createdPI = intellectualPropertyService.createIntellectualProperty(piDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPI);
    }

    /**
     * Endpoint para buscar uma Propriedade Intelectual pelo ID.
     * GET /api/intellectual-properties/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntellectualPropertyDto> getIntellectualPropertyById(@PathVariable Long id) {
        try {
            IntellectualPropertyDto piDto = intellectualPropertyService.getIntellectualPropertyById(id);
            return ResponseEntity.ok(piDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint para listar todas as Propriedades Intelectuais.
     * GET /api/intellectual-properties
     */
    @GetMapping
    public ResponseEntity<List<IntellectualPropertyDto>> getAllIntellectualProperties() {
        List<IntellectualPropertyDto> pis = intellectualPropertyService.getAllIntellectualProperties();
        return ResponseEntity.ok(pis);
    }

    /**
     * Endpoint para atualizar uma Propriedade Intelectual existente.
     * PUT /api/intellectual-properties/{id}*
     * O corpo da requisição deve ser um DTO completo do tipo específico (SoftwareDto, BrandDto, etc.).
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntellectualPropertyDto> updateIntellectualProperty(
            @PathVariable Long id,
            @Valid @RequestBody IntellectualPropertyDto piDto) {
        try {
            IntellectualPropertyDto updatedPI = intellectualPropertyService.updateIntellectualProperty(id, piDto);
            return ResponseEntity.ok(updatedPI);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            // Captura a exceção de mudança de tipo de PI
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para deletar uma Propriedade Intelectual pelo ID.
     * DELETE /api/intellectual-properties/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntellectualProperty(@PathVariable Long id) {
        try {
            intellectualPropertyService.deleteIntellectualProperty(id);
            return ResponseEntity.noContent().build(); // Status 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}