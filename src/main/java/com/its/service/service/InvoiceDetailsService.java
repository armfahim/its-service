package com.its.service.service;

import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.dto.InvoiceDetailsViewDto;
import com.its.service.dto.PaymentStatusDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.response.InvoiceDetailsResponse;
import com.its.service.utils.PaginatedResponse;

import java.util.List;

public interface InvoiceDetailsService extends GenericService<InvoiceDetails> {


    PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, Long supplierId,
                                    String fromInvoiceDate, String toInvoiceDate, Long branchId);

    List<InvoiceDetails> findAllByIsPaidFalse();

    InvoiceDetailsViewDto findViewById(Long id);

    void validateInvoiceNumber(String invoiceNumber);

    void isInvoiceNumberChanged(String changedInvoiceNumber, String existsInvoiceNumber);

    Object getDistinctInvoicesYearsAndMonths();

    List<InvoiceDetailsResponse> findBySupplier(Long id);

    InvoiceDetails updatePaymentStatus(PaymentStatusDto dto);

    InvoiceDetailsDto save(InvoiceDetailsDto dto);

    InvoiceDetailsDto update(InvoiceDetailsDto dto);

    List<InvoiceDetailsResponse> findBySupplierOrBranch(Long supplierId, Long branchId);
}
