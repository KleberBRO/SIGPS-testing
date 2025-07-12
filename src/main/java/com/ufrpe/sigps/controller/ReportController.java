package com.ufrpe.sigps.controller;

import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import com.ufrpe.sigps.dto.ReportRequestDto;
import com.ufrpe.sigps.service.report.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/intellectual-properties")
    public ResponseEntity<InputStreamResource> generatePIReport(@RequestBody ReportRequestDto request) throws IOException {

        ByteArrayInputStream bis = reportService.generatePIReport(request);

        HttpHeaders headers = new HttpHeaders();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileExtension = request.getFormat().toLowerCase();
        String fileName = "relatorio_pis_" + timestamp + "." + fileExtension;

        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        MediaType mediaType = "CSV".equalsIgnoreCase(request.getFormat()) ?
                MediaType.parseMediaType("text/csv") :
                MediaType.APPLICATION_PDF;

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(mediaType)
                .body(new InputStreamResource(bis));
    }

  // ENDPOINT PARA A PRÉ-VISUALIZAÇÃO
    @PostMapping("/intellectual-properties/preview-data")
    public ResponseEntity<List<IntellectualPropertyDto>> getPIReportData(@RequestBody ReportRequestDto request) {
        List<IntellectualPropertyDto> data = reportService.fetchPIReportData(request);
        return ResponseEntity.ok(data);
    }
}