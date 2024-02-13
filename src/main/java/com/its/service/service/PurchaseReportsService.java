package com.its.service.service;

import com.its.service.dto.InvoiceTotalAmountDto;
import com.its.service.dto.MonthlyInvoiceAmountDto;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.projection.InvoiceTotalAmountProjection;
import com.its.service.projection.MonthlyInvoiceTotalAmountProjection;
import com.its.service.repository.InvoiceDetailsRepository;
import com.its.service.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println(NumberUtils.getRoundOffValue(invoiceDetailsRepository.findPurchaseAmountBySupplier(supplierId).getTotalPurchase()));
        return NumberUtils.getRoundOffValue(invoiceDetailsRepository.findPurchaseAmountBySupplier(supplierId).getTotalPurchase());
    }

    public InvoiceTotalAmountDto getPurchaseAmountBySupplierAndYearInMonth(String year, Long supplierId) {
        if (Objects.isNull(year)) throw new AlreadyExistsException("Please provide a year in the bar chart");
        List<MonthlyInvoiceTotalAmountProjection> projectionsData = invoiceDetailsRepository.findPurchaseAmountBySupplierOrYearInMonth(year, supplierId);
        // Process the data and create a modified list
        InvoiceTotalAmountDto invoiceAmountDtos = new InvoiceTotalAmountDto();
        List<MonthlyInvoiceAmountDto> monthlyInvoiceAmountDtoList = new ArrayList<>();
        for (MonthlyInvoiceTotalAmountProjection originalProjection : projectionsData) {
            MonthlyInvoiceAmountDto monthlyInvoiceAmount = new MonthlyInvoiceAmountDto();
            monthlyInvoiceAmount.setMonth(originalProjection.getMonth());
            monthlyInvoiceAmount.setMonthlyTotal(originalProjection.getMonthlyTotal());
            monthlyInvoiceAmountDtoList.add(monthlyInvoiceAmount);
        }
        invoiceAmountDtos.setMonthlyInvoiceAmount(monthlyInvoiceAmountDtoList);
        BigDecimal totalPurchaseInYear = projectionsData.stream()
                .map(MonthlyInvoiceTotalAmountProjection::getMonthlyTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoiceAmountDtos.setTotalPurchaseInYear(NumberUtils.getRoundOffValue(totalPurchaseInYear));
        return invoiceAmountDtos;
    }
}
