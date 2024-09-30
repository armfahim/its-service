package com.its.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "APP_REPORT")
public class AppReportEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RPT_NO")
    private Long id;

    @NotNull
    @Column(name = "RPT_ID")
    private String rptId;

    @Column(name = "RPT_NAME")
    private String rptName;

    @Column(name = "RPT_MODULE")
    private String rptModule;

    @Column(name = "RPT_GROUP_ID")
    private String rptGroupId;

    @Column(name = "RPT_TITLE")
    private String rptTitle;

    @Column(name = "RPT_SUB_TITLE")
    private String rptSubTitle;

    @Column(name = "RPT_HEADER")
    private String rptHeader;

    @Column(name = "RPT_FOOTER")
    private String rptFooter;

    @Column(name = "RPT_HEADER_IMAGE")
    private String rptHeaderImage;

    @Column(name = "RPT_FOOTER_IMAGE")
    private String rptFooterImage;

    @Column(name = "RPT_DESCRIPTION")
    private String rptDescription;

    @Column(name = "RPT_FACILITY_NAME")
    private String rptFacilityName;

    @Column(name = "RPT_ADDRESS1")
    private String rptAddress1;

    @Column(name = "RPT_ADDRESS2")
    private String rptAddress2;

    @Column(name = "RPT_JSPR_NAME")
    private String rptJsprName;

    @Column(name = "RPT_JSPR_SUB_NAME")
    private String rptJsprSubName;

    @Column(name = "FACILITY_ALIAS")
    private String facilityAlias;

}
