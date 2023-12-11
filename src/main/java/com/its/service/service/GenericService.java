package com.its.service.service;


import com.its.service.utils.PaginatedResponse;

import java.util.List;

public interface GenericService<T> {

    T save(T t);

    T update(T t);

    T findById(Long id);

    List<T> findAll();

    PaginatedResponse list(String sort, int page, int size);

}
