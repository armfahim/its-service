package com.its.service.resource;

import com.its.service.config.jasper.CustomizeReport;
import com.its.service.service.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/report")
@RequiredArgsConstructor
@Slf4j
public class ReportResource extends BaseResource {

    private final ReportsService reportsService;

    @GetMapping("invoice-details")
    public ResponseEntity<byte[]> generateInvoiceDetailsPdf(@RequestParam(required = false) Long invoiceId) {

        String reportName = "invoice_view.jrxml";

        invoiceId = Objects.nonNull(invoiceId) ? invoiceId : null;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("INVOICE_ID", invoiceId);

        return reportsService.generatePdf(parameters, reportName);
    }

    @PostMapping("/paperwork-breakdown-report")
    public ResponseEntity<byte[]> paperworkBreakdownReport(@RequestBody String reqObj) throws IOException {
        CustomizeReport report = reportsService.paperworkBreakdownReport(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }
}
