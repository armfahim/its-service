package com.its.service.resource;

import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/supplier-details")
public class SupplierDetailsResource {

//    private final DisasterTypeService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody SupplierDetailsDto dto) {
        SupplierDetails supplierDetails = new SupplierDetails();
        dto.to(supplierDetails);
        supplierDetails = service.save(disasterType);
//        return ok(success(DisasterTypeDto.from(disasterType), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }
}
