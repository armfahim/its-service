package com.its.service.service;

import com.its.service.exception.AlreadyExistsException;
import com.its.service.projection.InvoiceTotalAmountProjection;
import com.its.service.repository.InvoiceDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseReportsService {

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    public Object getTotalPurchaseMonthWise(String year, String month) {
        InvoiceTotalAmountProjection totalAmount = invoiceDetailsRepository.findInvoiceTotalByYearAndMonth(year, month);
        return totalAmount;
    }

    public Object getPurchaseAmountBySupplier(Long supplierId) {
        return invoiceDetailsRepository.findPurchaseAmountBySupplier(supplierId);
    }

    public Object getPurchaseAmountBySupplierAndYearInMonth(String year, Long supplierId) {
        if (Objects.isNull(year)) throw new AlreadyExistsException("Please provide a year in the bar chart");
        return invoiceDetailsRepository.findPurchaseAmountBySupplierOrYearInMonth(year, supplierId);
    }
}
