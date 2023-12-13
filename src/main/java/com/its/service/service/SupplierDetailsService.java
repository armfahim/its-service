package com.its.service.service;

import com.its.service.entity.SupplierDetails;
import com.its.service.utils.PaginatedResponse;

public interface SupplierDetailsService extends GenericService<SupplierDetails>{

    PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String supplierId, String supplierName);
}
