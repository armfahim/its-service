package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.CustomMessagePresentException;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.InvoiceDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
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
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {
    private final InvoiceDetailsRepository repository;


    @Override
    public InvoiceDetails save(InvoiceDetails invoiceDetails) {
        return repository.save(invoiceDetails);
    }

    @Override
    public InvoiceDetails update(InvoiceDetails invoiceDetails) {
        if (Objects.isNull(invoiceDetails)) {
            throw new ResourceNotFoundException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(invoiceDetails.getId()) || invoiceDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new ResourceNotFoundException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            BasicAudit.setAttributeForCreateUpdate(invoiceDetails, true, RecordStatus.ACTIVE);
            invoiceDetails = save(invoiceDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Please provide unique data.The info you've provide are already exists!");
        }
        return invoiceDetails;
    }

    @Override
    public InvoiceDetails findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(InvoiceDetails invoiceDetails) {
        if (Objects.isNull(invoiceDetails)) {
            throw new ResourceNotFoundException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(invoiceDetails.getId()) || invoiceDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new ResourceNotFoundException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            repository.delete(invoiceDetails);
        } catch (Exception e) {
            throw new CustomMessagePresentException("Failed to deleted the data!");
        }
    }

    @Override
    public List<InvoiceDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        Page<InvoiceDetails> pageData = repository.findAll(PaginationUtils.getPageable(sort, dir, page, size));
        List<InvoiceDetailsDto> data = pageData.getContent().stream().map(InvoiceDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, Long supplierId,
                                           String fromInvoiceDate, String toInvoiceDate) {

//        supplierId = supplierId.equals("") ? null : supplierId;
        sort = sort.isEmpty() ? "invoice_number" : sort;

        Page<InvoiceDetails> pageData = repository.findByListAndSearch(supplierId, fromInvoiceDate, toInvoiceDate, PaginationUtils.getPageable(sort, dir, page, size));
        List<InvoiceDetailsDto> data = pageData.getContent().stream().map(InvoiceDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }
}
