package com.its.service.service;

import com.its.service.projection.InvoiceTotalAmountProjection;
import com.its.service.repository.InvoiceDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseReportsService {

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    public Object getTotalPurchaseMonthWise(String year, String month) {
        InvoiceTotalAmountProjection totalAmount = invoiceDetailsRepository.findInvoiceTotalByYearAndMonth(year, month);
        return totalAmount;
    }
}
