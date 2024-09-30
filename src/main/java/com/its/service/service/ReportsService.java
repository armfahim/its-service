package com.its.service.service;

import com.its.service.config.jasper.CustomizeReport;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ReportsService {

    ResponseEntity<byte[]> generatePdf(Map<String, Object> parameters, String reportName);

    CustomizeReport paperworkBreakdownReport(String reqObj);
}
