package com.ufrpe.sigps.service.report;

import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import com.ufrpe.sigps.dto.ReportRequestDto;
import com.ufrpe.sigps.service.pi.IntellectualPropertyService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final IntellectualPropertyService piService;
    private final PdfReportGenerator pdfGenerator;
    private final CsvReportGenerator csvGenerator;

    public ReportServiceImpl(IntellectualPropertyService piService, PdfReportGenerator pdfGenerator, CsvReportGenerator csvGenerator) {
        this.piService = piService;
        this.pdfGenerator = pdfGenerator;
        this.csvGenerator = csvGenerator;
    }

    @Override
    public ByteArrayInputStream generatePIReport(ReportRequestDto request) throws IOException {
        // 1. Busca os dados no banco de dados com base nos filtros
        // (Por enquanto, apenas por status. Pode ser expandido no futuro)
        List<IntellectualPropertyDto> pis = piService.getAllIntellectualProperties().stream()
                .filter(pi -> pi.getStatus() == request.getStatus())
                .collect(Collectors.toList());

        // 2. Chama o gerador correto com base no formato solicitado
        if ("PDF".equalsIgnoreCase(request.getFormat())) {
            return pdfGenerator.generate(pis, request.getFields());
        } else if ("CSV".equalsIgnoreCase(request.getFormat())) {
            return csvGenerator.generate(pis, request.getFields());
        } else {
            throw new IllegalArgumentException("Formato de relatório inválido: " + request.getFormat());
        }
    }

    @Override
    public List<IntellectualPropertyDto> fetchPIReportData(ReportRequestDto request) {
        // A lógica é a mesma da busca de dados do método anterior
        // Você pode até criar um método privado para não repetir código
        return piService.getAllIntellectualProperties().stream()
                .filter(pi -> pi.getStatus() == request.getStatus())
                .collect(Collectors.toList());
    }
}
