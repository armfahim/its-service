package com.its.service.projection;

import java.math.BigDecimal;

public interface MonthlyInvoiceTotalAmountProjection {

    int getMonth();

    BigDecimal getMonthlyTotal();
}
