package com.its.service.service;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.utils.PaginatedResponse;

import javax.validation.constraints.Email;
import java.util.List;

public interface SupplierDetailsService extends GenericService<SupplierDetails> {

    PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String supplierName);

    default public List<InvoiceDetails> findAllByIsPaidFalse() {
        return null;
    }

    void validateEmail(String email);

    void validatePhone(String phone);

    void isEmailChanged(String changedEmail, @Email(message = "Please provide a valid email address") String existsEmail);

    void isPhoneChanged(String changedPhone, String existsPhone);

    void validateSupplierName(String supplierName);

    void isSupplierNameChanged(String changedName, String existsName);
}
