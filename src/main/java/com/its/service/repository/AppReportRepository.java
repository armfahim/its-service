package com.its.service.repository;

import com.its.service.entity.AppReportEntity;
import com.its.service.service.AppReportService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppReportRepository extends JpaRepository<AppReportEntity, Long> {

    AppReportEntity findByRptId(String rptId);
}
