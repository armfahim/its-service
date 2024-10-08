package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.dto.UpdateStatusDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.helper.BasicAudit;
import com.its.service.service.SupplierDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/supplier-details")
@RequiredArgsConstructor
@Slf4j
public class SupplierDetailsResource {

    private final SupplierDetailsService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody SupplierDetailsDto dto) {
        service.validateSupplierName(dto.getSupplierName());
//        service.validateEmail(dto.getEmail());
//        service.validatePhone(dto.getPhone());
        SupplierDetails supplierDetails = new SupplierDetails();
        dto.to(supplierDetails);
        try {
            BasicAudit.setAttributeForCreateUpdate(supplierDetails, false, RecordStatus.ACTIVE);
            supplierDetails = service.save(supplierDetails);
        } catch (DataIntegrityViolationException e) {
            log.error(MessageConstant.INTERNAL_SERVER_ERROR, e);
            throw new AlreadyExistsException(MessageConstant.ALREADY_EXIST);
        }
        return ok(success(SupplierDetailsDto.from(supplierDetails), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody SupplierDetailsDto dto) {
        SupplierDetails supplierDetails = service.findById(dto.getId());
        service.isSupplierNameChanged(dto.getSupplierName(), supplierDetails.getSupplierName());
//        service.isEmailChanged(dto.getEmail(), supplierDetails.getEmail());
//        service.isPhoneChanged(dto.getPhone(), supplierDetails.getPhone());
        dto.to(supplierDetails);
        supplierDetails = service.update(supplierDetails);
        return ok(success(SupplierDetailsDto.from(supplierDetails), MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        SupplierDetails supplierDetails = service.findById(id);
        service.delete(supplierDetails);
        return ok(success(MessageConstant.DATA_DELETE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update-status")
    public ResponseEntity<Object> updateStatus(@RequestBody UpdateStatusDto dto) {
        SupplierDetails supplierDetails = service.findById(dto.getId());
        supplierDetails.setRecordStatus(dto.getStatus());
        return ok(success(SupplierDetailsDto.from(service.update(supplierDetails)), MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String orderColumnName,
                                       @RequestParam(value = "dir", defaultValue = "") String dir,
                                       @RequestParam(value = "supplierName", defaultValue = "") String supplierName) {
        return ok(paginatedSuccess(service.listAndSearch(orderColumnName, dir, page, size, supplierName)).getJson());
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAll() {
        List<SupplierDetailsDto> dtos = new ArrayList<>();
        try {
            List<SupplierDetails> supplierDetails = service.findAll();
            dtos = supplierDetails.stream().map(SupplierDetailsDto::from).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageConstant.NOT_FOUND);
        }
        return ok(success(dtos, MessageConstant.SUCCESS).getJson());
    }
}
