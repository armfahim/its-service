package com.its.service.service;

import com.its.service.constant.MessageConstant;
import com.its.service.entity.InvoiceDetails;
import com.its.service.exception.AppException;
import com.its.service.response.DashboardResponse;
import com.its.service.response.InvoiceDetailsResponse;
import com.its.service.utils.DateUtils;
import com.its.service.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final InvoiceDetailsService invoiceDetailsService;
    private final SupplierDetailsService supplierDetailsService;

    public DashboardResponse getHighlights() {
        try {
            List<InvoiceDetails> invoiceDetailsPaidFalse = invoiceDetailsService.findAllByIsPaidFalse();
            List<InvoiceDetailsResponse> dueInvDetailsResponses = invoiceDetailsPaidFalse
                    .stream()
                    .filter(invoice -> invoice.getPaymentDueDate().isBefore(LocalDate.now()))
                    .map(item -> DashboardResponse.mapToInvoiceDetailsResponse(item))
                    .toList();

            DashboardResponse responses = new DashboardResponse();
            responses.setDueInvoices(dueInvDetailsResponses);
            BigDecimal netDue = dueInvDetailsResponses
                    .stream()
                    .map(data -> data.getNetDue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            responses.setNetDueOfDueInvoices(NumberUtils.getRoundOffValue(netDue));

            /**
             * Get invoice list that are set to become due in 1 day(static) or more days
             * Used fixed/static 1 day parameter to get the total pending invoice's due amount,
             * that have least 1 day to make due payment or more than 1 day,including current day.
             */
            BigDecimal netPending = invoiceDetailsPaidFalse
                    .stream()
                    .filter(invoice -> DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate()).getDays() >= 1)
                    .map(data -> data.getNetDue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            responses.setNetDueOfPendingInvoices(NumberUtils.getRoundOffValue(netPending));
            /***/

            responses.setTotalDueAmount(NumberUtils.getRoundOffValue(netDue.add(netPending)));
            responses.setTotalInvoices((int) getTotalInvoices());
            responses.setTotalSuppliers((int) getTotalSuppliers());

            return responses;
        } catch (Exception e) {
            log.error("Failed ", e);
            throw new AppException(MessageConstant.NOT_FOUND);
        }
    }


    private long getTotalSuppliers() {
        return supplierDetailsService.findAll().stream().count();
    }

    private long getTotalInvoices() {
        return invoiceDetailsService.findAll().stream().count();
    }

    public List<InvoiceDetailsResponse> getPendingInvoices(int dayToSelectPendingInvoice) {
        return invoiceDetailsService.findAllByIsPaidFalse()
                .stream()
                .filter(invoice -> DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate()).getDays() >= dayToSelectPendingInvoice)
                .map(item -> DashboardResponse.mapToInvoiceDetailsResponse(item))
                .collect(Collectors.toList());
    }
}
