package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.SupplierDetailsService;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierDetailsServiceImpl implements SupplierDetailsService {

    private final SupplierDetailsRepository repository;

    @Override
    @Transactional
    public SupplierDetails save(SupplierDetails supplierDetails) {
        return repository.save(supplierDetails);
    }

    @Override
    public SupplierDetails update(SupplierDetails supplierDetails) {
        if (Objects.isNull(supplierDetails)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(supplierDetails.getId()) || supplierDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            supplierDetails = save(supplierDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Please provide unique data.The info you've provide are already exists!");
        }
        return supplierDetails;
    }

    @Override
    public SupplierDetails findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlreadyExistsException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(SupplierDetails supplierDetails) {
        if (Objects.isNull(supplierDetails)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(supplierDetails.getId()) || supplierDetails.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            repository.delete(supplierDetails);
        } catch (Exception e) {
            throw new AlreadyExistsException("Failed to deleted the data!");
        }
    }

    @Override
    public List<SupplierDetails> findAll() {
        return repository.findAllByRecordStatus(RecordStatus.ACTIVE);
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        Page<SupplierDetails> pageData = repository.findAll(PaginationUtils.getPageable(sort, dir, page, size));
        List<SupplierDetailsDto> data = pageData.getContent().stream().map(SupplierDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String supplierName) {
        supplierName = supplierName.equals("") ? null : supplierName;
        sort = sort.isEmpty() ? "supplierName" : sort;

        Page<SupplierDetails> pageData = repository.findByListAndSearch(supplierName
                , PaginationUtils.getPageable(sort, dir, page, size));
        List<SupplierDetailsDto> data = pageData.getContent().stream().map(SupplierDetailsDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public void validateEmail(String email) {
        Optional<SupplierDetails> optionalSupplierDetails = repository.findByEmailAndRecordStatus(email, RecordStatus.ACTIVE);
        if (optionalSupplierDetails.isPresent())
            throw new AlreadyExistsException(optionalSupplierDetails.get().getSupplierName() + " has the same email. Please provide unique email");
    }

    @Override
    public void validatePhone(String phone) {
        Optional<SupplierDetails> optionalSupplierDetails = repository.findByPhoneAndRecordStatus(phone, RecordStatus.ACTIVE);
        if (optionalSupplierDetails.isPresent())
            throw new AlreadyExistsException(optionalSupplierDetails.get().getSupplierName() + " has the same phone number. Please provide unique phone number");
    }

    @Override
    public void isEmailChanged(String changedEmail, String existsEmail) {
        if (!changedEmail.equalsIgnoreCase(existsEmail)) this.validateEmail(changedEmail);
    }

    @Override
    public void isPhoneChanged(String changedPhone, String existsPhone) {
        if (!changedPhone.equalsIgnoreCase(existsPhone)) this.validatePhone(changedPhone);
    }


}
