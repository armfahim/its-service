package com.its.service.service;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.ShopBranch;
import com.its.service.entity.SupplierDetails;
import com.its.service.utils.PaginatedResponse;

import javax.validation.constraints.Email;
import java.util.List;

public interface ShopBranchService extends GenericService<ShopBranch> {

    PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String shopBranchName);
}
