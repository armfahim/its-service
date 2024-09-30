/**
 *
 */
package com.its.service.dto.report;

import lombok.Data;

@Data
public class CommonRequestDto {

    private String reportFormat = "PDF";
    private String deptFlag = "A";
    private Long userNo;
    private Long id;
    private String dateRange = "TODAY";
    private String startDate;
    private String endDate;

}
