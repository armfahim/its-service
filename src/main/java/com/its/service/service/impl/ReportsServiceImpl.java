package com.its.service.service.impl;

import com.its.service.entity.InvoiceDetails;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.repository.InvoiceDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.service.ReportsService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final HikariDataSource dataSource;

    private static final String JASPER_TEMPLATE_PATH = "classpath:reports/";

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    private final InvoiceDetailsService invoiceDetailsService;

    @Override
    public ResponseEntity<byte[]> generatePdf(Map<String, Object> params, String reportName) {
        try {
            Locale locale = Locale.US;
            JasperPrint invoiceViewReport = new JasperPrint();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(locale);
            parameters.putAll(params);

            Connection dbConn = dataSource.getConnection();
            InputStream in = getClass().getResourceAsStream("/reports/" + reportName);
            invoiceViewReport = JasperFillManager.fillReport(
                    JasperCompileManager
//                            .compileReport(ResourceUtils.getFile(JASPER_TEMPLATE_PATH + reportName).getAbsolutePath()),
                            .compileReport(getClass().getResourceAsStream("/reports/" + reportName)),
                    parameters, dbConn);

            InvoiceDetails invoiceDetails = invoiceDetailsService.findById((Long) params.get("INVOICE_ID"));

            HttpHeaders headers = new HttpHeaders();
            // set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename",
                    invoiceDetails.getInvoiceNumber() + "_" + invoiceDetails.getSupplierDetails().getSupplierName() + "_" + ".pdf");

            // create the report in PDF format
            return new ResponseEntity<>(JasperExportManager.exportReportToPdf(invoiceViewReport), headers,
                    HttpStatus.OK);
        } catch (JRException | SQLException e) {
            log.error("error", e);
            throw new AlreadyExistsException("PDF generation failed. Internal server error [" + e + "]");
        }
    }

    // Fill template order parametres
    private Map<String, Object> parameters(Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("LOGO_PATH", JASPER_TEMPLATE_PATH);
        parameters.put("REPORT_LOCALE", locale);
        return parameters;
    }
}
