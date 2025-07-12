package com.ufrpe.sigps.service.report;

import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvReportGenerator {

    public ByteArrayInputStream generate(List<IntellectualPropertyDto> pis, List<String> fields) throws IOException {
        final CSVFormat format = CSVFormat.DEFAULT.withHeader(fields.toArray(new String[0]));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

            for (IntellectualPropertyDto pi : pis) {
                // Para cada PI, cria uma lista de valores na ordem dos campos solicitados
                List<String> data = fields.stream()
                        .map(field -> getFieldValue(pi, field))
                        .collect(Collectors.toList());
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // Método auxiliar para pegar o valor do campo dinamicamente
    private String getFieldValue(IntellectualPropertyDto pi, String fieldName) {
        Object value = switch (fieldName.toLowerCase()) {
            case "title" -> pi.getTitle();
            case "inventorname" -> pi.getInventorName();
            case "status" -> pi.getStatus().toString();
            case "requestdate" -> pi.getRequestDate() != null ? pi.getRequestDate().toString() : "";
            case "grantdate" -> pi.getGrantDate() != null ? pi.getGrantDate().toString() : "";
            case "expirationdate" -> pi.getExpirationDate() != null ? pi.getExpirationDate().toString() : "";
            default -> "Campo não mapeado";
        };
        return value != null ? value.toString() : "";
    }
}
