package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.service.SupplierDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        supplierDetails = service.save(supplierDetails);
        return ok(success(SupplierDetailsDto.from(supplierDetails), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }
}
