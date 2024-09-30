package com.its.service.service.impl;

import com.its.service.entity.AppReportEntity;
import com.its.service.repository.AppReportRepository;
import com.its.service.service.AppReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppReportServiceImpl implements AppReportService {

    private final AppReportRepository repository;

    @Override
    public AppReportEntity findById(Long id) {
        return null;
    }

    @Override
    public AppReportEntity findByReportId(String reportId) {
        return repository.findByRptId(reportId);
    }
}
