package com.its.service.utils;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class ReportResponse {
    private boolean success = true;
    private String message = null;

    private Map<String, Object> model;
    private List items;
    private Object obj;
}
