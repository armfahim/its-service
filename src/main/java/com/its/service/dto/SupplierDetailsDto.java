package com.its.service.dto;

import com.its.service.entity.SupplierDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SupplierDetailsDto {

    private Long id;
    private String supplierID;
    private String supplierName;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private String status;


    public static SupplierDetailsDto from(SupplierDetails supplierDetails) {
        SupplierDetailsDto dto = new SupplierDetailsDto();
        BeanUtils.copyProperties(supplierDetails, dto);
        return dto;
    }

    public void to(SupplierDetails supplierDetails) {
        supplierDetails.setSupplierID(supplierID);
        supplierDetails.setSupplierName(supplierName);
        supplierDetails.setContactPerson(contactPerson);
        supplierDetails.setPhone(phone);
        supplierDetails.setEmail(email);
        supplierDetails.setAddress(address);
    }
}
