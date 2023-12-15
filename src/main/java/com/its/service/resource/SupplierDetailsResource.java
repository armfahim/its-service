package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.dto.UpdateStatusDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.helper.BasicAudit;
import com.its.service.service.SupplierDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/supplier-details")
public class SupplierDetailsResource {

    private final SupplierDetailsService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody SupplierDetailsDto dto) {
        SupplierDetails supplierDetails = new SupplierDetails();
        dto.to(supplierDetails);
        BasicAudit.setAttributeForCreateUpdate(supplierDetails, false, RecordStatus.ACTIVE);
        try {
            supplierDetails = service.save(supplierDetails);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Please provide unique data.The info you've provide are already exists!");
        }
        return ok(success(SupplierDetailsDto.from(supplierDetails), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody SupplierDetailsDto dto) {
        SupplierDetails supplierDetails = service.findById(dto.getId());
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
        service.update(supplierDetails);
        return ok(success(dto, MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String orderColumnName,
                                       @RequestParam(value = "dir", defaultValue = "") String dir,
                                       @RequestParam(value = "supplierId", defaultValue = "") String supplierId,
                                       @RequestParam(value = "supplierName", defaultValue = "") String supplierName) {
        return ok(paginatedSuccess(service.listAndSearch(orderColumnName, dir, page, size, supplierId, supplierName)).getJson());
    }
}
