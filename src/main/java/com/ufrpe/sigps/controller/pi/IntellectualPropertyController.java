// src/main/java/com/ufrpe/sigps/controller/pi/IntellectualPropertyController.java
package com.ufrpe.sigps.controller.pi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import com.ufrpe.sigps.model.Inventor;
import com.ufrpe.sigps.service.pi.IntellectualPropertyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/intellectual-properties")
public class IntellectualPropertyController {

    @Autowired
    private ObjectMapper objectMapper;

    private final IntellectualPropertyService intellectualPropertyService;

    public IntellectualPropertyController(IntellectualPropertyService intellectualPropertyService) {
        this.intellectualPropertyService = intellectualPropertyService;
    }

    /**
     * Endpoint para criar uma nova Propriedade Intelectual com upload de arquivo.
     * Recebe um DTO e um arquivo via multipart/form-data
     * POST /api/intellectual-properties
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createIntellectualProperty(
            @RequestPart("piData") String piDataJson,
            // Recebe uma lista de arquivos para a chave "documents"
            @RequestPart(value = "documents", required = false) List<MultipartFile> documentFiles,
            // Recebe uma lista de arquivos para a chave "images"
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles
    ) throws JsonProcessingException {

        IntellectualPropertyDto piDto = objectMapper.readValue(piDataJson, IntellectualPropertyDto.class);

        // Chama o novo método do serviço que aceita as listas de arquivos
        IntellectualPropertyDto createdPI = intellectualPropertyService.createIntellectualProperty(piDto, documentFiles, imageFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPI);
    }

    /**
     * Endpoint para buscar uma Propriedade Intelectual pelo ID
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
     * Busca PIs pelo título.
     * Exemplo: GET /api/intellectual-properties/title?value=Vinho do Vale
     */
    @GetMapping("/title")
    public ResponseEntity<List<IntellectualPropertyDto>> getIntellectualPropertyByTitle
    (@RequestParam ("value") String title) {
        try {
            List<IntellectualPropertyDto> pis = intellectualPropertyService.getIntellectualPropertyByTitle(title);
            return ResponseEntity.ok(pis);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Busca PIs pelo nome do inventor
     * Exemplo: GET /api/intellectual-properties/inventor?value=Alvaro2
     */
    @GetMapping("/inventor")
    public ResponseEntity<List<IntellectualPropertyDto>> getIntellectualPropertyByInventor
    (@RequestParam ("value") String inventorName) {
        try {
            List<IntellectualPropertyDto> pis = intellectualPropertyService.getIntellectualPropertyByInventorName(inventorName);
            return ResponseEntity.ok(pis);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Busca PIs pela data de requisição (formato YYYY-MM-DD).
     * Exemplo: GET /api/intellectual-properties/request-date?date=2024-01-15
     */
    @GetMapping("/request-date")
    public ResponseEntity<List<IntellectualPropertyDto>> getIntellectualPropertyByRequestDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<IntellectualPropertyDto> pis = intellectualPropertyService.getIntellectualPropertyByRequestDate(date);
            return ResponseEntity.ok(pis);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    /**
     * Busca PIs pela data de concessão (formato YYYY-MM-DD).
     * Exemplo: GET /api/intellectual-properties/grant-date?date=2024-01-15
     */
    @GetMapping("/grant-date")
    public ResponseEntity<List<IntellectualPropertyDto>> getIntellectualPropertyByGrantDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<IntellectualPropertyDto> pis = intellectualPropertyService.getIntellectualPropertyByGrantDate(date);
            return ResponseEntity.ok(pis);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    /**
     * Busca PIs pela data de vencimento (formato YYYY-MM-DD).
     * Exemplo: GET /api/intellectual-properties/expiration-date?date=2024-01-15
     */
    @GetMapping("/expiration-date")
    public ResponseEntity<List<IntellectualPropertyDto>> getIntellectualPropertyByExpirationDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<IntellectualPropertyDto> pis = intellectualPropertyService.getIntellectualPropertyByExpirationDate(date);
            return ResponseEntity.ok(pis);
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
     * Endpoint para atualizar uma Propriedade Intelectual existente
     * PUT /api/intellectual-properties/{id}
     * O corpo da requisição deve ser um DTO completo do tipo específico (SoftwareDto, BrandDto, etc)
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
     * Endpoint para deletar uma Propriedade Intelectual pelo ID
     * DELETE /api/intellectual-properties/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntellectualProperty(@PathVariable Long id) {
        try {
            intellectualPropertyService.deleteIntellectualProperty(id);
            return ResponseEntity.noContent().build(); // Status: 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}