/**
 *
 */
package com.its.service.config.jasper;

import com.ctc.wstx.shaded.msv_core.grammar.xmlschema.Field;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BaseJasperService {

    /* new update by input stream end */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ByteArrayOutputStream generateReport(CustomizeReport reportDef) throws Exception {

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Exporter exporter = generateExporter(reportDef);
        exporter.setExporterOutput(getExporterOutput(reportDef.getReportFormat(), byteArray));
        JasperPrint jasperPrint = generatePrinter(reportDef);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();

        return byteArray;
    }

    private JasperPrint generatePrinter(CustomizeReport reportDef) throws JRException, IOException {

        String reportUrl = reportDef.getReportDir() + reportDef.getReportName() + ".jasper";
        String fileName = reportUrl.substring(1, reportUrl.length());

        Map<String, Object> paramMap = reportDef.getParameters();
        String subRpt = (String) paramMap.get("SUBREPORT_DIR");
        if (!StringUtils.isBlank(subRpt)) {
            subRpt = subRpt.substring(1, subRpt.length());
            paramMap.put("SUBREPORT_DIR", subRpt);
        }

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            return null;
        }

        JasperPrint jasperPrint = null;
        JRDataSource jrDataSource = reportDef.getDataSource();
        if (jrDataSource == null && reportDef.getReportData() != null && !reportDef.getReportData().isEmpty()) {
            jrDataSource = new JRBeanCollectionDataSource(reportDef.getReportData());
        }
        if (jrDataSource == null) {
            jasperPrint = JasperFillManager.fillReport(inputStream, paramMap, jrDataSource);
        } else {
            jasperPrint = JasperFillManager.fillReport(inputStream, paramMap, jrDataSource);
        }
        return jasperPrint;
    }

    /* new update by input stream end */

    public ByteArrayOutputStream generateBatchPdfReport(List<CustomizeReport> jasperReportList) throws Exception {

        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        if (null != jasperReportList && jasperReportList.size() > 0) {

            for (CustomizeReport reportDef : jasperReportList) {

                JasperPrint jp = generatePrinter(reportDef);
                jasperPrints.add(jp);

            }

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            exporter.exportReport();
        }

        return byteArray;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ByteArrayOutputStream generateBatchReport(List<CustomizeReport> jasperReportList) throws Exception {

        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        if (null != jasperReportList && jasperReportList.size() > 0) {

            Exporter exporter = null;

            for (CustomizeReport reportDef : jasperReportList) {

                if (exporter == null) {
                    exporter = generateExporter(reportDef);
                    exporter.setExporterOutput(getExporterOutput(reportDef.getReportFormat(), byteArray));
                }

                JasperPrint jp = generatePrinter(reportDef);
                jasperPrints.add(jp);

            }

            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            exporter.exportReport();
        }

        return byteArray;
    }

    private static ExporterOutput getExporterOutput(JasperExportFormat format,
                                                    ByteArrayOutputStream byteArrayOutputStream) throws Exception {
        switch (format) {
            case PDF_FORMAT:
            case XLS_FORMAT:
            case XLSX_FORMAT:
            case PPTX_FORMAT:
            case DOCX_FORMAT:
            case ODT_FORMAT:
            case ODS_FORMAT:
                return new SimpleOutputStreamExporterOutput(byteArrayOutputStream);

            case HTML_FORMAT:
                return new SimpleHtmlExporterOutput(byteArrayOutputStream);
            case CSV_FORMAT:
                return new SimpleWriterExporterOutput(byteArrayOutputStream);
            case RTF_FORMAT:
                return new SimpleWriterExporterOutput(byteArrayOutputStream);
            default:
                throw new Exception("Invalid format");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Exporter generateExporter(CustomizeReport reportDef) throws Exception {

        Exporter exporter = getExporter(reportDef.getReportFormat());

        if (reportDef.getReportFormat().getExtension().equals("xlsx")) {

            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();

            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true); // Detect cell types (date and etc.)
            configuration.setWhitePageBackground(false); // No white background!
            configuration.setFontSizeFixEnabled(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);

            exporter.setConfiguration(configuration);

        }
        return exporter;
    }

    @SuppressWarnings({"rawtypes", "unused"})
    private static void applyCustomConfiguration(Field[] fields, Exporter exporter, Map<String, Object> configuration) {

    }

    @SuppressWarnings({"rawtypes", "unused"})
    private static void applyDefaultConfiguration(Exporter exporter, CustomizeReport format) {
        // TODO: based on format apply common configuration
    }

    @SuppressWarnings("rawtypes")
    private static Exporter getExporter(JasperExportFormat format) throws Exception {
        switch (format) {
            case PDF_FORMAT:
                return new JRPdfExporter();
            case HTML_FORMAT:
                return new HtmlExporter();
            case CSV_FORMAT:
                return new JRCsvExporter();
            case XLS_FORMAT:
                return new JRXlsExporter();
            case RTF_FORMAT:
                return new JRRtfExporter();
            case ODT_FORMAT:
                return new JROdtExporter();
            case ODS_FORMAT:
                return new JROdsExporter();
            case DOCX_FORMAT:
                return new JRDocxExporter();
            case XLSX_FORMAT:

                return new JRXlsxExporter();
            case PPTX_FORMAT:
                return new JRPptxExporter();
            default:
                throw new Exception("Invalid format");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> CustomizeReport bindJasperReport(Map<String, Object> reportObj, Object reportDto) {

        List<T> reportData = new ArrayList<T>();

        reportData.add((T) reportDto);

        CustomizeReport report = new CustomizeReport();
        report.setOutputFilename((String) reportObj.get("outPutFileName"));
        report.setReportName((String) reportObj.get("reportName"));
        report.setReportDir(JsCommonFunction.getResoucePath((String) reportObj.get("reportDir")) + "/");
        report.setReportFormat(JsCommonFunction.printFormat((String) reportObj.get("printFormat")));
        report.setReportData(reportData);
        if (null != reportObj.get("parameterMap")) {
            report.setParameters((Map<String, Object>) reportObj.get("parameterMap"));
        }

        return report;
    }

}
