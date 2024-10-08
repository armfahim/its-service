/**
 * 
 */
package com.its.service.config.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class CustomizeReport implements Serializable {

	private static final long serialVersionUID = 1L;

	String reportName;
	String reportDir;

	Map<String, Object> parameters = new HashMap<String, Object>();

	JasperExportFormat reportFormat = JasperExportFormat.PDF_FORMAT;

	Collection<?> reportData;
	JRDataSource dataSource;

	Boolean useDefaultConfiguration;
	Map<String, Object> reportConfiguration = new HashMap<String, Object>();

	byte[] content;

	String outputFilename;

	public Resource getReport() throws FileNotFoundException {
		String reportPath = reportDir + reportName;
		Resource result = new FileSystemResource(reportPath + ".jasper");
		if (result.exists()) {
			return result;
		}

		result = new FileSystemResource(reportPath + ".jrxml");
		if (result.exists()) {
			return result;
		}
		throw new FileNotFoundException(
				"Report [" + reportPath + ".jasper" + "] or [" + reportPath + ".jrxml] file not found");
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDir() {
		return reportDir;
	}

	public void setReportDir(String reportDir) {
		this.reportDir = reportDir;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public JasperExportFormat getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(JasperExportFormat reportFormat) {
		this.reportFormat = reportFormat;
	}

	public Collection<?> getReportData() {
		return reportData;
	}

	public void setReportData(Collection<?> reportData) {
		this.reportData = reportData;
	}

	public JRDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Boolean getUseDefaultConfiguration() {
		return useDefaultConfiguration;
	}

	public void setUseDefaultConfiguration(Boolean useDefaultConfiguration) {
		this.useDefaultConfiguration = useDefaultConfiguration;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, Object> getReportConfiguration() {
		return reportConfiguration;
	}

	public void setReportConfiguration(Map<String, Object> reportConfiguration) {
		this.reportConfiguration = reportConfiguration;
	}

}
