package com.ufrpe.sigps.service.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfReportGenerator {

    public ByteArrayInputStream generate(List<IntellectualPropertyDto> pis, List<String> fields) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Relatório de Propriedades Intelectuais").setBold().setFontSize(16));

        Table table = new Table(fields.size());

        // Adiciona os cabeçalhos da tabela
        for (String field : fields) {
            table.addHeaderCell(new Cell().add(new Paragraph(field).setBold()));
        }

        // Adiciona os dados
        for (IntellectualPropertyDto pi : pis) {
            for (String field : fields) {
                table.addCell(new Cell().add(new Paragraph(getFieldValue(pi, field))));
            }
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método auxiliar (pode ser o mesmo do gerador CSV ou estar em uma classe utilitária)
    private String getFieldValue(IntellectualPropertyDto pi, String fieldName) {
        Object value = switch (fieldName.toLowerCase()) {
            case "title" -> pi.getTitle();
            case "inventorname" -> pi.getInventorName();
            case "status" -> pi.getStatus().toString();
            case "requestdate" -> pi.getRequestDate() != null ? pi.getRequestDate().toString() : "";
            case "grantdate" -> pi.getGrantDate() != null ? pi.getGrantDate().toString() : "";
            case "expirationdate" -> pi.getExpirationDate() != null ? pi.getExpirationDate().toString() : "";
            default -> "";
        };
        return value != null ? value.toString() : "";
    }
}
