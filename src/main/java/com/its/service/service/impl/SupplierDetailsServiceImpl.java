package com.its.service.service.impl;

import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.SupplierDetailsService;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierDetailsServiceImpl implements SupplierDetailsService {

    private final SupplierDetailsRepository repository;

    @Override
    public SupplierDetails save(SupplierDetails supplierDetails) {
        return repository.save(supplierDetails);
    }

    @Override
    public SupplierDetails update(SupplierDetails supplierDetails) {
        return null;
    }

    @Override
    public SupplierDetails findById(Long id) {
        return null;
    }

    @Override
    public List<SupplierDetails> findAll() {
        return null;
    }

    @Override
    public PaginatedResponse list(String sort, int page, int size) {
        Page<SupplierDetails> pageData = repository.findAll(PaginationUtils.getPageable(sort, page, size));
        List<SupplierDetailsDto> data = pageData.getContent().stream().map(SupplierDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }
}
