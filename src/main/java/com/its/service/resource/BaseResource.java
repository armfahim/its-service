/**
 *
 */
package com.its.service.resource;

import com.its.service.config.jasper.CustomizeReport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class BaseResource {

    public ResponseEntity<byte[]> respondReportOutput(CustomizeReport jasperReport, boolean forceDownload)
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

    public ResponseEntity<byte[]> respondReportOutputWithoutHeader(CustomizeReport jasperReport,
                                                                   boolean forceDownload) throws IOException {
        if (jasperReport == null || jasperReport.getContent() == null) {
            throw new FileNotFoundException("jasper Report Not found");
        } else {
            String outputFileName = (jasperReport.getOutputFilename()) + "."
                    + jasperReport.getReportFormat().getExtension();
            String contentDisposition = forceDownload == true ? "attachment;filename=\"" + outputFileName + "\""
                    : "filename=\"" + outputFileName + "\"";
            return ResponseEntity.ok()
                    .header("Content-Type", jasperReport.getReportFormat().getMimeType() + ";charset=UTF-8")
                    .header("Content-Disposition", contentDisposition).body(jasperReport.getContent());

        }
    }

}
