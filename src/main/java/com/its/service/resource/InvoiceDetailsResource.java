package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.EnumDTO;
import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.dto.UpdateStatusDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.enums.Term;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.CustomMessagePresentException;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.utils.EnumConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/invoice-details")
@RequiredArgsConstructor
public class InvoiceDetailsResource {

    private final InvoiceDetailsService service;
    private final SupplierDetailsRepository supplierDetailsRepository;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody InvoiceDetailsDto dto) {
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        dto.to(invoiceDetails);
        BasicAudit.setAttributeForCreateUpdate(invoiceDetails, false, RecordStatus.ACTIVE);
        SupplierDetails supplierDetails = new SupplierDetails();
        try {
            if (Objects.nonNull(dto.getSupplierDetails()))
                supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new ResourceNotFoundException("No supplier is available"));
            invoiceDetails.setSupplierDetails(supplierDetails);
            invoiceDetails = service.save(invoiceDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException(MessageConstant.ALREADY_EXIST);
        }
        return ok(success(InvoiceDetailsDto.from(invoiceDetails), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody InvoiceDetailsDto dto) {
        InvoiceDetails invoiceDetails = service.findById(dto.getId());
        dto.to(invoiceDetails);

        SupplierDetails supplierDetails = new SupplierDetails();
        try {
            if (Objects.nonNull(dto.getSupplierDetails()))
                supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new ResourceNotFoundException("No supplier is available"));
            invoiceDetails.setSupplierDetails(supplierDetails);
            invoiceDetails = service.update(invoiceDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException(MessageConstant.ALREADY_EXIST);
        }
        return ok(success(InvoiceDetailsDto.from(invoiceDetails), MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        InvoiceDetails invoiceDetails = service.findById(id);
        service.delete(invoiceDetails);
        return ok(success(MessageConstant.DATA_DELETE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update-status")
    public ResponseEntity<Object> updateStatus(@RequestBody UpdateStatusDto dto) {
        InvoiceDetails invoiceDetails = service.findById(dto.getId());
        invoiceDetails.setRecordStatus(dto.getStatus());
        return ok(success(InvoiceDetailsDto.from(service.update(invoiceDetails)), MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String orderColumnName,
                                       @RequestParam(value = "dir", defaultValue = "") String dir,
                                       @RequestParam(value = "supplier", defaultValue = "") Long supplierId,
                                       @RequestParam(value = "fromInvoiceDate", defaultValue = "") String fromInvoiceDate,
                                       @RequestParam(value = "toInvoiceDate", defaultValue = "") String toInvoiceDate) {
        try {
            return ok(paginatedSuccess(service.listAndSearch(orderColumnName, dir, page, size, supplierId, fromInvoiceDate, toInvoiceDate)).getJson());
        } catch (Exception e) {
            throw new CustomMessagePresentException("Internal Server error");
        }

    }

    @GetMapping(value = "/get-term")
    public ResponseEntity<Object> getTerm() {
        try {
            List<EnumDTO> enumList = EnumConversion.enumToKeyValue(Term.class);
            return ok(success(enumList, MessageConstant.SUCCESS).getJson());
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageConstant.NOT_FOUND);
        }
    }
}
