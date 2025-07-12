package com.ufrpe.sigps.dto;

import com.ufrpe.sigps.model.IntellectualPropertyStatus;
import lombok.Data;
import java.util.List;

@Data
public class ReportRequestDto {
    // Filtro para as PIs (ex: IN_PROCESSING)
    private IntellectualPropertyStatus status;

    // Lista de campos/colunas a serem impressos (ex: "title", "requestDate")
    private List<String> fields;

    // Formato do arquivo de saída ("PDF" ou "CSV")
    private String format;
}
