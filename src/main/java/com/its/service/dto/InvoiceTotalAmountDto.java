package com.its.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
public class InvoiceTotalAmountDto {

    private String totalPurchaseInYear;

    private List<MonthlyInvoiceAmountDto> monthlyInvoiceAmount;
}
