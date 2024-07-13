package com.its.service.dto.paperworks;

import com.its.service.entity.paperwork.CashPurchase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CashPurchaseDto {

    private Long id;
    private BigDecimal cashPurchaseAmount;
    private String itemName;

    public static CashPurchase to(CashPurchase cashPurchase, CashPurchaseDto dto) {
        cashPurchase.setItemName(dto.getItemName());
        cashPurchase.setCashPurchaseAmount(Objects.nonNull(dto.getCashPurchaseAmount()) ? dto.getCashPurchaseAmount() : BigDecimal.ZERO);
        return cashPurchase;
    }

    public static CashPurchaseDto from(CashPurchase cashPurchase) {
        CashPurchaseDto dto = new CashPurchaseDto();
        BeanUtils.copyProperties(cashPurchase, dto);
        return dto;
    }
}
