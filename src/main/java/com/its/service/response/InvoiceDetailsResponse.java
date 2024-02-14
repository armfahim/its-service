package com.its.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class InvoiceDetailsResponse {

    private Long id;
    private int paymentDueInDays;
    private int paymentDueInMonth;
    private int paymentDueInYears;
    private String invoiceNumber;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate invoiceDate;

    private LocalDate paymentDueDate;

    private BigDecimal invoiceAmount;
    private BigDecimal netDue;
    private Boolean isPaid;
    private String supplierName;

    private String chequeNumber;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paidDate;
    private String invoiceDesc;
}
