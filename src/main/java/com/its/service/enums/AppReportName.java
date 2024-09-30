/**
 *
 */
package com.its.service.enums;

import lombok.Getter;

@Getter
public enum AppReportName {
    DISCHARGED_PATIENT_REPORT("DISCHARGED_PATIENT_REPORT", "Discharged Patient Report");


    private String name;
    private String id;

    AppReportName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getById(String id) {

        for (AppReportName r : AppReportName.values()) {
            if (r.getId().equals(id))
                return r.getName();
        }
        return "unknown";
    }

    public static String getByName(String name) {
        for (AppReportName r : AppReportName.values()) {
            if (r.getName().equals(name))
                return r.getId();
        }
        return "0";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

}
