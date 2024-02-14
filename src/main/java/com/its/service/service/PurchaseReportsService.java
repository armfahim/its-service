package com.its.service.service;

import com.its.service.dto.InvoiceTotalAmountDto;
import com.its.service.dto.MonthlyInvoiceAmountDto;
import com.its.service.enums.Month;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReportsService {

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    public Object getTotalPurchaseMonthWise(String year, String month) {
        InvoiceTotalAmountProjection totalAmount = invoiceDetailsRepository.findInvoiceTotalByYearAndMonth(year, month);
        return totalAmount;
    }

    public Object getPurchaseAmountBySupplier(Long supplierId) {
        InvoiceTotalAmountProjection projection = invoiceDetailsRepository.findPurchaseAmountBySupplier(supplierId);
        return Objects.nonNull(projection) ? NumberUtils.getRoundOffValue(projection.getTotalPurchase()) : null;
    }

    public InvoiceTotalAmountDto getPurchaseAmountBySupplierAndYearInMonth(String year, Long supplierId) {
        if (Objects.isNull(year)) throw new AlreadyExistsException("Please provide a year in the bar chart");
        List<MonthlyInvoiceTotalAmountProjection> projectionsData = invoiceDetailsRepository.findPurchaseAmountBySupplierOrYearInMonth(year, supplierId);
        if (projectionsData.isEmpty()) return null;
        // Process the data and create a modified list
        InvoiceTotalAmountDto invoiceAmountDtos = new InvoiceTotalAmountDto();
        List<MonthlyInvoiceAmountDto> monthlyInvoiceAmountDtoList = new ArrayList<>();

        Map<Integer, BigDecimal> monthlyInvoiceAmount = projectionsData.stream()
                .collect(Collectors.toMap(MonthlyInvoiceTotalAmountProjection::getMonth, MonthlyInvoiceTotalAmountProjection::getMonthlyTotal));
        List<Integer> monthValues = Month.getDisplayNameInList();

        monthlyInvoiceAmountDtoList = monthValues.stream()
                .map(month -> {
                    MonthlyInvoiceAmountDto dto = new MonthlyInvoiceAmountDto();
                    dto.setMonth(month);
                    dto.setMonthlyTotal(monthlyInvoiceAmount.getOrDefault(month, BigDecimal.ZERO));
                    return dto;
                })
                .collect(Collectors.toList());

        invoiceAmountDtos.setMonthlyInvoiceAmount(monthlyInvoiceAmountDtoList);
        BigDecimal totalPurchaseInYear = projectionsData.stream()
                .map(MonthlyInvoiceTotalAmountProjection::getMonthlyTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoiceAmountDtos.setTotalPurchaseInYear(NumberUtils.getRoundOffValue(totalPurchaseInYear));
        return invoiceAmountDtos;
    }
}
