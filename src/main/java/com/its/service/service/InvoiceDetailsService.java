package com.its.service.service;

import com.its.service.dto.InvoiceDetailsViewDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.PaginatedResponse;

import java.util.List;

public interface InvoiceDetailsService extends GenericService<InvoiceDetails> {


    PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, Long supplierId,
                                    String fromInvoiceDate, String toInvoiceDate);

    List<InvoiceDetails> findAllByIsPaidFalse();

    InvoiceDetailsViewDto findViewById(Long id);
}
