// src/main/java/com/ufrpe/sigps/service/report/ReportService.java
package com.ufrpe.sigps.service.report;

import com.ufrpe.sigps.dto.IntellectualPropertyDto;
import com.ufrpe.sigps.dto.ReportRequestDto;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ReportService {
    ByteArrayInputStream generatePIReport(ReportRequestDto request) throws IOException;

    List<IntellectualPropertyDto> fetchPIReportData(ReportRequestDto request);
}