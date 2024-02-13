package com.its.service.response;

import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
public class DashboardResponse {

    private int totalSuppliers;
    private int totalInvoices;
    private List<InvoiceDetailsResponse> dueInvoices;
    //Used String instead of BigDecimal due to format the number in commas //11,289.00
    private String netDueOfDueInvoices;
    private String netDueOfPendingInvoices;
    private String totalDueAmount;

    public static InvoiceDetailsResponse mapToInvoiceDetailsResponseForPending(InvoiceDetails invoice) {
        Period period = DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate());
        return InvoiceDetailsResponse.builder()
                .paymentDueDate(invoice.getPaymentDueDate())
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .netDue(invoice.getNetDue())
                .supplierName(invoice.getSupplierDetails().getSupplierName())
                .paymentDueInDays(period.getDays())
                .isPaid(invoice.getIsPaid())
                .paymentDueInMonth(period.getMonths())
                .paymentDueInYears(period.getYears())
                .invoiceDate(invoice.getInvoiceDate())
                .build();
    }

    public static InvoiceDetailsResponse mapToInvoiceDetailsResponse(InvoiceDetails invoice) {
        Period period = DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate());
        return InvoiceDetailsResponse.builder()
                .paymentDueDate(invoice.getPaymentDueDate())
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .netDue(invoice.getNetDue())
                .supplierName(invoice.getSupplierDetails().getSupplierName())
                .paymentDueInDays(Math.abs(period.getDays()))
                .isPaid(invoice.getIsPaid())
                .paymentDueInMonth(Math.abs(period.getMonths()))
                .paymentDueInYears(Math.abs(period.getYears()))
                .invoiceDate(invoice.getInvoiceDate())
                .invoiceAmount(invoice.getInvoiceAmount())
                .build();
    }
}
