package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.dto.InvoiceDetailsViewDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.repository.InvoiceDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.utils.DateUtils;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {
    private final InvoiceDetailsRepository repository;


    @Override
    @Transactional
    public InvoiceDetails save(InvoiceDetails invoiceDetails) {
        if (Boolean.TRUE.equals(invoiceDetails.getIsPaid()) && Objects.isNull(invoiceDetails.getPaidDate())) {
            throw new AlreadyExistsException("Please provide paid date.");
        }
        return repository.save(invoiceDetails);
    }

    @Override
    public InvoiceDetails update(InvoiceDetails invoiceDetails) {
        if (Objects.isNull(invoiceDetails)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(invoiceDetails.getId()) || invoiceDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            invoiceDetails = save(invoiceDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Internal Server Error");
        }
        return invoiceDetails;
    }

    @Override
    public InvoiceDetails findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlreadyExistsException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(InvoiceDetails invoiceDetails) {
        if (Objects.isNull(invoiceDetails)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(invoiceDetails.getId()) || invoiceDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            repository.delete(invoiceDetails);
        } catch (Exception e) {
            throw new AlreadyExistsException("Failed to deleted the data!");
        }
    }

    @Override
    public List<InvoiceDetails> findAll() {
        return repository.findAllByRecordStatusAndSupplierDetailsRecordStatus(RecordStatus.ACTIVE, RecordStatus.ACTIVE);
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

        LocalDate fromDate = StringUtils.isNotEmpty(fromInvoiceDate) ? DateUtils.asLocalDate(fromInvoiceDate) : null;
        LocalDate toDate = StringUtils.isNotEmpty(toInvoiceDate) ? DateUtils.asLocalDate(toInvoiceDate) : null;

        sort = sort.isEmpty() ? "invoiceDate" : sort;
        Page<InvoiceDetails> pageData = repository.findByListAndSearch(supplierId, fromDate, toDate, PaginationUtils.getPageable(sort, dir, page, size));

        List<InvoiceDetailsDto> data = pageData.getContent().stream().map(InvoiceDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public List<InvoiceDetails> findAllByIsPaidFalse() {
        return repository.findAllByRecordStatusAndIsPaidFalseAndSupplierDetailsRecordStatus(RecordStatus.ACTIVE, RecordStatus.ACTIVE);
    }

    @Override
    public InvoiceDetailsViewDto findViewById(Long id) {
        InvoiceDetails invoiceDetails = findById(id);
        return InvoiceDetailsViewDto.from(invoiceDetails);
    }

    @Override
    public void validateInvoiceNumber(String invoiceNumber) {
        Optional<InvoiceDetails> optionalInvoiceDetails = repository.findByInvoiceNumberAndRecordStatus(invoiceNumber, RecordStatus.ACTIVE);
        if (optionalInvoiceDetails.isPresent()) throw new AlreadyExistsException("Invoice number already exists.");
    }

    @Override
    public void isInvoiceNumberChanged(String changedInvoiceNumber, String existsInvoiceNumber) {
        if (!changedInvoiceNumber.equals(existsInvoiceNumber)) this.validateInvoiceNumber(changedInvoiceNumber);
    }
}
