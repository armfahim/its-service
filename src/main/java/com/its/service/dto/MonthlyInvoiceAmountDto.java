package com.its.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MonthlyInvoiceAmountDto {

    private int month;
    private BigDecimal monthlyTotal;

}
