/**
 * 
 */
package com.its.service.dto.report;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Tamim Answari
 *
 */
@Data
public class CommonReportDto {

	private String reportTitle;
	private String facilityName;
	private String customerLogo;
	private String facilityAddress;
	private String facilityAddress2;
	private String departmentName;
	private String dateCriteria;
	private String reportFormat;
	private String printFormat;
	private String reportType;
	private String workStationName;
	private Date fromDate;
	private Date toDate;
	private String rptGroupBy;
	private String searchLabel;
	private String searchBy;

	private boolean showHeader = true;
	private boolean showFooter = true;

	private List<?> dataList;
	
	private List<?> reportDataList;


}
