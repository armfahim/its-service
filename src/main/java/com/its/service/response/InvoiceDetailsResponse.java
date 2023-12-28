package com.its.service.response;

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
    private LocalDate invoiceDate;
    private LocalDate paymentDueDate;
    private BigDecimal netDue;
    private Boolean isPaid;
    private String supplierName;
}
