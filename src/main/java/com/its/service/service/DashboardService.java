package com.its.service.service;

import com.its.service.constant.MessageConstant;
import com.its.service.exception.AppException;
import com.its.service.exception.CustomMessagePresentException;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.response.DashboardResponse;
import com.its.service.response.InvoiceDetailsResponse;
import com.its.service.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final InvoiceDetailsService invoiceDetailsService;
    private final SupplierDetailsService supplierDetailsService;

    public DashboardResponse getHighlights(int dayToSelectDueInvoice) {
        try {
            List<InvoiceDetailsResponse> pendingInvDetailsResponses = invoiceDetailsService
                    .findAllByIsPaidFalse()
                    .stream()
                    .filter(invoice -> DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate()).getDays() >= dayToSelectDueInvoice)
                    .map(item -> DashboardResponse.mapToInvoiceDetailsResponseForDue(item))
                    .collect(Collectors.toList());

            List<InvoiceDetailsResponse> dueInvDetailsResponses = invoiceDetailsService
                    .findAllByIsPaidFalse()
                    .stream()
                    .filter(invoice -> invoice.getPaymentDueDate().isBefore(LocalDate.now()))
                    .map(item -> DashboardResponse.mapToInvoiceDetailsResponseForDue(item))
                    .toList();

            DashboardResponse responses = new DashboardResponse();
            responses.setPendingInvoices(pendingInvDetailsResponses);
            responses.setDueInvoices(dueInvDetailsResponses);
            responses.setTotalInvoices(getTotalInvoices());
            responses.setTotalSuppliers(getTotalSuppliers());

            return responses;
        } catch (Exception e) {
            log.error("Failed ", e);
            throw new AppException(MessageConstant.NOT_FOUND);
        }
    }


    private int getTotalSuppliers() {
        return supplierDetailsService.findAll().size();
    }

    private int getTotalInvoices() {
        return invoiceDetailsService.findAll().size();
    }
}
