/**
 * 
 */
package com.its.service.service;


import com.its.service.entity.AppReportEntity;

public interface AppReportService {

	AppReportEntity findById(Long id);

	AppReportEntity findByReportId(String reportId);

}
