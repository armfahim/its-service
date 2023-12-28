package com.its.service.service;


import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.PaginatedResponse;

import java.util.List;

public interface GenericService<T> {

    T save(T t);

    T update(T t);

    T findById(Long id);

    void delete(T t);

    List<T> findAll();

    PaginatedResponse list(String sort, String dir, int page, int size);

}
