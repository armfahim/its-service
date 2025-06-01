package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.*;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.ShopBranch;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.Month;
import com.its.service.enums.RecordStatus;
import com.its.service.enums.Term;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.AppException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.InvoiceDetailsRepository;
import com.its.service.repository.ShopBranchRepository;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.response.DashboardResponse;
import com.its.service.response.InvoiceDetailsResponse;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.utils.DateUtils;
import com.its.service.utils.EnumConversion;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {

    private final InvoiceDetailsRepository repository;
    private final SupplierDetailsRepository supplierDetailsRepository;
    private final ShopBranchRepository shopBranchRepository;

    @Override
    @Transactional
    public InvoiceDetailsDto save(InvoiceDetailsDto dto) {
        validateInvoiceNumber(dto.getInvoiceNumber());
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        dto.to(invoiceDetails);
        try {

            if (Objects.nonNull(dto.getSupplierDetails())) {
                SupplierDetails supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new AlreadyExistsException("No supplier is available"));
                invoiceDetails.setSupplierDetails(supplierDetails);
            }

            if (Objects.nonNull(dto.getShopBranch())) {
                ShopBranch shopBranch = shopBranchRepository.findById(dto.getShopBranch()).orElseThrow(() -> new AlreadyExistsException("No Branch is found"));
                invoiceDetails.setShopBranch(shopBranch);
            }

            BasicAudit.setAttributeForCreateUpdate(invoiceDetails, false, RecordStatus.ACTIVE);
            return InvoiceDetailsDto.from(this.save(invoiceDetails));
        } catch (Exception e) {
            log.error("Failed to save invoice", e);
            throw new AlreadyExistsException(MessageConstant.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public InvoiceDetails save(InvoiceDetails invoiceDetails) {
        return repository.save(invoiceDetails);
    }

    @Override
    @Transactional
    public InvoiceDetailsDto update(InvoiceDetailsDto dto) {
        InvoiceDetails invoiceDetails = findById(dto.getId());
        isInvoiceNumberChanged(dto.getInvoiceNumber(), invoiceDetails.getInvoiceNumber());
        dto.to(invoiceDetails);

        if (Objects.nonNull(dto.getSupplierDetails())) {
            SupplierDetails supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new AppException("No supplier is available"));
            invoiceDetails.setSupplierDetails(supplierDetails);
        }
        if (Objects.nonNull(dto.getShopBranch())) {
            ShopBranch shopBranch = shopBranchRepository.findById(dto.getShopBranch()).orElseThrow((() -> new AppException("No shop branch is found")));
            invoiceDetails.setShopBranch(shopBranch);
        }

        return InvoiceDetailsDto.from(this.update(invoiceDetails));
    }

    @Override
    @Transactional
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
                                           String fromInvoiceDate, String toInvoiceDate, Long branchId) {

        LocalDate fromDate = StringUtils.isNotEmpty(fromInvoiceDate) ? DateUtils.asLocalDate(fromInvoiceDate) : null;
        LocalDate toDate = StringUtils.isNotEmpty(toInvoiceDate) ? DateUtils.asLocalDate(toInvoiceDate) : null;

        sort = sort.isEmpty() ? "invoiceDate" : sort;
        Page<InvoiceDetails> pageData = repository.findByListAndSearch(supplierId, branchId, fromDate, toDate, PaginationUtils.getPageable(sort, dir, page, size));

        List<InvoiceDetailsDto> data = pageData.getContent().stream().map(InvoiceDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public List<InvoiceDetails> findAllByIsPaidFalse() {
        return repository.findAllByRecordStatusAndIsPaidFalseAndTermNotAndSupplierDetailsRecordStatus(RecordStatus.ACTIVE, Term.COD, RecordStatus.ACTIVE);
    }

    @Override
    public InvoiceDetailsViewDto findViewById(Long id) {
        InvoiceDetails invoiceDetails = findById(id);
        return InvoiceDetailsViewDto.from(invoiceDetails);
    }

    @Override
    public void validateInvoiceNumber(String invoiceNumber) {
        Optional<InvoiceDetails> optionalInvoiceDetails = repository.findByInvoiceNumberAndRecordStatus(invoiceNumber, RecordStatus.ACTIVE);
        if (!optionalInvoiceDetails.isEmpty()) throw new AlreadyExistsException("Invoice number already exists.");
    }

    @Override
    public void isInvoiceNumberChanged(String changedInvoiceNumber, String existsInvoiceNumber) {
        if (!changedInvoiceNumber.equals(existsInvoiceNumber)) this.validateInvoiceNumber(changedInvoiceNumber);
    }

    @Override
    public Object getDistinctInvoicesYearsAndMonths() {
        List<EnumDTO> monthEnums = EnumConversion.enumToKeyValue(Month.class);

        return YearMonthDto.builder()
                .years(repository.findDistinctInvoiceYearByInvoiceDate())
                .months(monthEnums)
                .currentYear(String.valueOf(DateUtils.getCurrentYear()))
                .build();
    }

    @Override
    public List<InvoiceDetailsResponse> findBySupplier(Long id) {
        List<InvoiceDetails> invoiceDetails = repository.findBySupplierDetailsIdAndRecordStatus(id, RecordStatus.ACTIVE);
        if (invoiceDetails.isEmpty())
            throw new AlreadyExistsException("No invoices have been found with this supplier");
        return invoiceDetails.stream()
                .map(DashboardResponse::mapToInvoiceDetailsResponse)
                .toList();
    }

    @Override
    public List<InvoiceDetailsResponse> findBySupplierOrBranch(Long supplierId, Long branchId) {
        List<InvoiceDetails> invoiceDetails;
        if (Objects.isNull(supplierId) && Objects.isNull(branchId)) {
            return new ArrayList<>();
        } else if (Objects.isNull(branchId)) {
            invoiceDetails = repository.findBySupplierDetailsIdAndRecordStatus(supplierId, RecordStatus.ACTIVE);
        } else if (Objects.isNull(supplierId)) {
            invoiceDetails = repository.findByShopBranchIdAndRecordStatus(branchId, RecordStatus.ACTIVE);
        } else {
            invoiceDetails = repository.findBySupplierDetailsIdAndShopBranchIdAndRecordStatus(supplierId,branchId, RecordStatus.ACTIVE);
        }
        if (invoiceDetails.isEmpty())
            throw new AlreadyExistsException("No purchase record have been found!");
        return invoiceDetails.stream()
                .map(DashboardResponse::mapToInvoiceDetailsResponse)
                .toList();
    }

    @Override
    public InvoiceDetails updatePaymentStatus(PaymentStatusDto dto) {
        InvoiceDetails invoiceDetails = this.findById(dto.getId());
        dto.to(invoiceDetails);
        return this.update(invoiceDetails);
    }

}
