package com.its.service.config.jasper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportPathAndName {

    private String pClient;
    private String pLayout;
    private String rPath;
    private String rName;
}
