package com.its.service.response;

import com.its.service.dto.paperworks.CashPurchaseDto;
import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class PaperworkBreakdownViewResponse {
    private List<PaperworkBreakdownDto> paperworkBreakdownDtoList;

    private static BigDecimal totalMerchantSales = BigDecimal.ZERO;
    private static BigDecimal totalSalesTax = BigDecimal.ZERO;
    private static BigDecimal totalInsideSales = BigDecimal.ZERO;
    private static BigDecimal totalSalesRecord = BigDecimal.ZERO;

    private static BigDecimal totalCreditCard = BigDecimal.ZERO;
    private static BigDecimal totalDebitCard = BigDecimal.ZERO;
    private static BigDecimal totalCreditDebitCard = BigDecimal.ZERO;
    private static BigDecimal totalEBT = BigDecimal.ZERO;
    private static BigDecimal totalExpense = BigDecimal.ZERO;
    private static BigDecimal totalOfficeExpense = BigDecimal.ZERO;
    private static BigDecimal totalTrustFund = BigDecimal.ZERO;
    private static BigDecimal totalHouseAc = BigDecimal.ZERO;
    private static BigDecimal totalStoreDeposit = BigDecimal.ZERO;
    private static BigDecimal totalCashPurchase = BigDecimal.ZERO;

    private static BigDecimal totalInsideSalesBreakdown = BigDecimal.ZERO;
    private static BigDecimal totalCashOverShort = BigDecimal.ZERO;
    
    private List<CashPurchaseDto> cashPurchaseList;

    public static PaperworkBreakdownViewResponse from(List<PaperworkBreakdownDto> dtos) {
        PaperworkBreakdownViewResponse response = new PaperworkBreakdownViewResponse();
        response.setPaperworkBreakdownDtoList(dtos);

        for (PaperworkBreakdownDto dto : dtos) {
            totalMerchantSales = totalMerchantSales.add(dto.getMerchantSale());
            totalSalesTax = totalSalesTax.add(dto.getSalesTax());
            totalInsideSales = totalInsideSales.add(dto.getTotalInsideSales());
            totalSalesRecord = totalSalesRecord.add(dto.getTotalSalesRecord());

            totalCreditCard = totalCreditCard.add(dto.getCreditCard());
        }
        
        return response;
    }
}
