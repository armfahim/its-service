package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.EnumDTO;
import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.dto.PaymentStatusDto;
import com.its.service.dto.UpdateStatusDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.enums.Term;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.exception.AppException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.utils.EnumConversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/invoice-details")
@RequiredArgsConstructor
@Slf4j
public class InvoiceDetailsResource {

    private final InvoiceDetailsService service;
    private final SupplierDetailsRepository supplierDetailsRepository;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody InvoiceDetailsDto dto) {
        service.validateInvoiceNumber(dto.getInvoiceNumber());
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        dto.to(invoiceDetails);
        try {
            SupplierDetails supplierDetails = new SupplierDetails();
            if (Objects.nonNull(dto.getSupplierDetails()))
                supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new AlreadyExistsException("No supplier is available"));
            invoiceDetails.setSupplierDetails(supplierDetails);
            BasicAudit.setAttributeForCreateUpdate(invoiceDetails, false, RecordStatus.ACTIVE);
            invoiceDetails = service.save(invoiceDetails);
        } catch (Exception e) {
            log.error("Failed to save invoice", e);
            throw new AlreadyExistsException(MessageConstant.INTERNAL_SERVER_ERROR);
        }
        return ok(success(InvoiceDetailsDto.from(invoiceDetails), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody InvoiceDetailsDto dto) {
        InvoiceDetails invoiceDetails = service.findById(dto.getId());
        service.isInvoiceNumberChanged(dto.getInvoiceNumber(), invoiceDetails.getInvoiceNumber());
        dto.to(invoiceDetails);

        SupplierDetails supplierDetails = new SupplierDetails();
        if (Objects.nonNull(dto.getSupplierDetails()))
            supplierDetails = supplierDetailsRepository.findById(dto.getSupplierDetails()).orElseThrow(() -> new AppException("No supplier is available"));
        invoiceDetails.setSupplierDetails(supplierDetails);
        invoiceDetails = service.update(invoiceDetails);
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
            throw new AlreadyExistsException("Internal Server error");
        }

    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAll() {
        List<InvoiceDetailsDto> dtos = new ArrayList<>();
        try {
            List<InvoiceDetails> invoiceDetails = service.findAll();
            dtos = invoiceDetails.stream().map(InvoiceDetailsDto::from).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AlreadyExistsException(MessageConstant.NOT_FOUND);
        }
        return ok(success(dtos, MessageConstant.SUCCESS).getJson());
    }

    @GetMapping(value = "/get-term")
    public ResponseEntity<Object> getTerm() {
        try {
            List<EnumDTO> enumList = EnumConversion.enumToKeyValue(Term.class);
            return ok(success(enumList, MessageConstant.SUCCESS).getJson());
        } catch (Exception e) {
            throw new AlreadyExistsException(MessageConstant.NOT_FOUND);
        }
    }

    @GetMapping(value = "/find/supplier/{id}")
    public ResponseEntity<Object> find(@PathVariable Long id) {
        return ok(success((service.findBySupplier(id))).getJson());
    }

    @GetMapping(value = "/find-view/{id}")
    public ResponseEntity<Object> findView(@PathVariable Long id) {
        return ok(success(service.findViewById(id)).getJson());
    }

    @GetMapping(value = "/distinct/years-months")
    public ResponseEntity<Object> getInvoicesYearsAndMonths() {
        return ResponseEntity.ok(success(service.getDistinctInvoicesYearsAndMonths()).getJson());
    }

    @PutMapping(value = "/update-payment-status")
    public ResponseEntity<Object> updatePaymentStatus(@RequestBody PaymentStatusDto dto) {
        return ok(success(PaymentStatusDto.from(service.updatePaymentStatus(dto)), MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }
}
