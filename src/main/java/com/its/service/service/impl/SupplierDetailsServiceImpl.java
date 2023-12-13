package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.CustomMessagePresentException;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.SupplierDetailsService;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(supplierDetails)) {
            throw new ResourceNotFoundException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(supplierDetails.getId()) || supplierDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new ResourceNotFoundException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            BasicAudit.setAttributeForCreateUpdate(supplierDetails,true, RecordStatus.ACTIVE);
            supplierDetails = save(supplierDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Please provide unique data.The info you've provide are already exists!");
        }
        return supplierDetails;
    }

    @Override
    public SupplierDetails findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(SupplierDetails supplierDetails) {
        if (Objects.isNull(supplierDetails)) {
            throw new ResourceNotFoundException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(supplierDetails.getId()) || supplierDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new ResourceNotFoundException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            repository.delete(supplierDetails);
        } catch (Exception e) {
            throw new CustomMessagePresentException("Failed to deleted the data!");
        }
    }

    @Override
    public List<SupplierDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        Page<SupplierDetails> pageData = repository.findAll(PaginationUtils.getPageable(sort, dir, page, size));
        List<SupplierDetailsDto> data = pageData.getContent().stream().map(SupplierDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }
}
