package com.its.service.service;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.utils.PaginatedResponse;

public interface InvoiceDetailsService extends GenericService<InvoiceDetails>{

    PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String supplierName);
}
