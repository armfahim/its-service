package com.its.service.service;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.utils.PaginatedResponse;

import java.util.List;

public interface SupplierDetailsService extends GenericService<SupplierDetails>{

    PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String supplierName);

    default public List<InvoiceDetails> findAllByIsPaidFalse(){return null;}
}
