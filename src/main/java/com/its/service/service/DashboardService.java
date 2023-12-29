package com.its.service.service;

import com.its.service.constant.MessageConstant;
import com.its.service.entity.InvoiceDetails;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.response.DashboardResponse;
import com.its.service.response.InvoiceDetailsResponse;
import com.its.service.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InvoiceDetailsService invoiceDetailsService;
    private final SupplierDetailsService supplierDetailsService;

    public DashboardResponse getHighlights(int dayToSelectDueInvoice) {
        try {
            List<InvoiceDetailsResponse> invDetailsResponses = invoiceDetailsService
                    .findAllByIsPaidFalse()
                    .stream()
                    .filter(invoice -> DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate()).getDays() >= dayToSelectDueInvoice)
                    .map(this::mapToInvoiceDetailsResponse)
                    .collect(Collectors.toList());

            DashboardResponse responses = new DashboardResponse();
            responses.setInvoiceDetails(invDetailsResponses);
            responses.setTotalInvoices(getTotalInvoices());
            responses.setTotalSuppliers(getTotalSuppliers());

            return responses;
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageConstant.NOT_FOUND);
        }
    }

    private InvoiceDetailsResponse mapToInvoiceDetailsResponse(InvoiceDetails invoice) {
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


    private int getTotalSuppliers() {
        return supplierDetailsService.findAll().size();
    }

    private int getTotalInvoices() {
        return invoiceDetailsService.findAll().size();
    }
}
