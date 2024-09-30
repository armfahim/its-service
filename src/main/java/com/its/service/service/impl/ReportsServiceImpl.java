package com.its.service.service.impl;

import com.its.service.config.jasper.BaseJasperService;
import com.its.service.config.jasper.CustomizeReport;
import com.its.service.config.jasper.JsCommonFunction;
import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.dto.report.CommonReportDto;
import com.its.service.dto.report.CommonRequestDto;
import com.its.service.entity.AppReportEntity;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.enums.AppReportName;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.service.AppReportService;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.service.ReportsService;
import com.its.service.service.paperworks.PaperworkBreakdownService;
import com.its.service.utils.CommonFunctions;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportsServiceImpl extends CommonFunctions implements ReportsService {

    private final HikariDataSource dataSource;

    private static final String JASPER_TEMPLATE_PATH = "classpath:reports/";

    private final InvoiceDetailsService invoiceDetailsService;
    private final AppReportService appReportService;
    private final BaseJasperService baseJasperService;
    private final PaperworkBreakdownService paperworkBreakdownService;

    @Override
    public ResponseEntity<byte[]> generatePdf(Map<String, Object> params, String reportName) {
        try {
            Locale locale = Locale.US;
            JasperPrint invoiceViewReport = new JasperPrint();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(locale);
            parameters.putAll(params);

            Connection dbConn = dataSource.getConnection();

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

    @Override
    public CustomizeReport paperworkBreakdownReport(String reqObj) {
        CommonRequestDto reqDto = deserializeObject(reqObj, CommonRequestDto.class);
        if (reqDto == null) {
            return null;
        }

        CommonReportDto reportDto = new CommonReportDto();

        List<PaperworkBreakdown> dataObj = paperworkBreakdownService.findAllPaperworkBreakdownByPaperworkId(reqDto.getId());
        if (Objects.nonNull(dataObj)) {
            List<PaperworkBreakdownDto> dataList = dataObj.stream().map(PaperworkBreakdownDto::from).toList();
            reportDto.setDataList(dataList);
        }

        AppReportEntity appReportEntity = appReportService.findByReportId(AppReportName.DISCHARGED_PATIENT_REPORT.getId());
//        reportDto.setCustomerLogo(getCustomerLogo(appReportEntity.getRptHeaderImage()));

        CustomizeReport report = JsCommonFunction.bindReport(appReportEntity, reportDto, reqDto.getReportFormat());

        ByteArrayOutputStream baos = null;
        try {
            baos = baseJasperService.generateReport(report);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JsCommonFunction.finallyOutputStream(baos);
        }

        CustomizeReport reportFinal = new CustomizeReport();
        reportFinal.setContent(baos.toByteArray());
        return reportFinal;
    }

    // Fill template order parametres
    private Map<String, Object> parameters(Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("LOGO_PATH", JASPER_TEMPLATE_PATH);
        parameters.put("REPORT_LOCALE", locale);
        return parameters;
    }
}
