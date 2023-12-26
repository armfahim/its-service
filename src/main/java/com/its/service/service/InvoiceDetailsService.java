package com.its.service.service;

import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.PaginatedResponse;

public interface InvoiceDetailsService extends GenericService<InvoiceDetails> {


    PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, Long supplierId,
                                    String fromInvoiceDate, String toInvoiceDate);
}
