/**
 *
 */
package com.its.service.config.jasper;

import com.its.service.dto.report.CommonReportDto;
import com.its.service.entity.AppReportEntity;
import com.its.service.service.AppReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Component
public class JsCommonFunction {

    @Autowired
    HttpSession httpSession;

    @Autowired
    static AppReportService appReportService;

/*    static int reportStaticPathFlag;
    static String reportStaticPathUrl;

    @Value("${report.static-path-flag}")
    public void setFlag(int flag) {
        JsCommonFunction.reportStaticPathFlag = flag;
    }

    @Value("${report.static-path-url}")
    public void setUrl(String url) {
        JsCommonFunction.reportStaticPathUrl = url;
    }*/

    static final List<ReportPathAndName> reportNamList = new ArrayList<ReportPathAndName>();

    static {
        reportNamList
                .add(new ReportPathAndName("global", "DISCHARGED_PATIENT_REPORT", "/", "discharged_patient"));

    }

    public static ResponseEntity<byte[]> respondReportOutput(CustomizeReport jasperReport, boolean forceDownload)
            throws IOException {
        if (jasperReport == null || jasperReport.getContent() == null) {
            throw new FileNotFoundException("jasper Report Not found");
        } else {
            String outputFileName = (jasperReport.getOutputFilename()) + "."
                    + jasperReport.getReportFormat().getExtension();
            String contentDisposition = forceDownload == true ? "attachment;filename=\"" + outputFileName + "\""
                    : "filename=\"" + outputFileName + "\"";
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", "*")
                    .header("Content-Type", jasperReport.getReportFormat().getMimeType() + ";charset=UTF-8")
                    .header("Content-Disposition", contentDisposition).body(jasperReport.getContent());

        }
    }

    public static JasperExportFormat printFormat(String printFormat) {

        if (printFormat == null) {
            return JasperExportFormat.PDF_FORMAT;
        }

        if (printFormat.startsWith("PDF")) {
            return JasperExportFormat.PDF_FORMAT;
        } else if (printFormat.startsWith("HTML")) {
            return JasperExportFormat.HTML_FORMAT;
        } else if (printFormat.startsWith("DOCX")) {
            return JasperExportFormat.DOCX_FORMAT;
        } else if (printFormat.startsWith("XLSX")) {
            return JasperExportFormat.XLSX_FORMAT;
        }

        return JasperExportFormat.PDF_FORMAT;
    }

    public static String getReportPath(HttpServletRequest request, String path) {
        return request.getServletContext().getRealPath(path);

    }

    // public static String getResoucePath(String filePath) {
//		Resource resource = new ClassPathResource(filePath);
//		try {
//			return resource.getFile().getAbsolutePath();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
    public static String getResoucePath(String filePath) {

        return filePath;
    }

    public static File getResouceFile(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        try {
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getResouceFileByte(File file) {

        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResouceFileBase64String(byte[] fileByte) {

        return Base64.getEncoder().encodeToString(fileByte);
    }

    public static long generateRandom(int length) {
        while (true) {
            long numb = (long) (Math.random() * 100000000 * 1000000);
            if (String.valueOf(numb).length() == length)
                return numb;
        }
    }

    public void setHttpSession(String key, Object obj) {

        httpSession.setAttribute(key, obj);
    }

    public Object getHttpSession(String key) {
        return httpSession.getAttribute(key);
    }

    public static ReportPathAndName reportPathName(String pClient, String pLayout) {
        return reportNamList.stream().filter(rp -> rp.getPClient().equals(pClient) && rp.getPLayout().equals(pLayout))
                .findAny().orElse(null);
    }

    public static BigDecimal subtractBigDecimal(BigDecimal val1, BigDecimal val2) {

        if (null != val1 && null != val2) {
            return val1.subtract(val2);
        }

        return null;
    }

    public static String stringAbbreviate(String str, int lenth) {

        String output = (str.length() > lenth) ? str.substring(0, lenth - 1).concat("...") : str;

        return output;
    }

    public static void finallyOutputStream(ByteArrayOutputStream baos) {

        if (baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CustomizeReport bindReport(AppReportEntity appReportEntity, CommonReportDto reportDto,
                                             String reportFormat) {

        if (appReportEntity == null) {
            System.out.println("App Report Not Found");
            return null;
        }

        List<CommonReportDto> reportList = new ArrayList<CommonReportDto>();
        ReportPathAndName reportPathAndName = reportPathName(appReportEntity.getFacilityAlias(),
                appReportEntity.getRptId());

        if (reportPathAndName == null) {
            System.out.println("Report Path Not Found");
            return null;
        }

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("SUBREPORT_DIR", getResoucePath(reportPathAndName.getRPath()));

        reportDto.setReportTitle(appReportEntity.getRptTitle());
        reportDto.setFacilityName(appReportEntity.getRptFacilityName());
        reportDto.setFacilityAddress(appReportEntity.getRptAddress1());
        reportDto.setFacilityAddress2(appReportEntity.getRptAddress2());
        reportList.add(reportDto);

        CustomizeReport report = new CustomizeReport();
        report.setOutputFilename(appReportEntity.getRptName());
        report.setReportName(reportPathAndName.getRName());
        report.setReportDir(getResoucePath(reportPathAndName.getRPath()) + "/");
        report.setReportFormat(printFormat(reportFormat));
        report.setParameters(parameterMap);
        report.setReportData(reportList);

        return report;
    }

}
