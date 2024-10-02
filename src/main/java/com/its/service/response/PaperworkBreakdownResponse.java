package com.its.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.dto.paperworks.CashPurchaseDto;
import com.its.service.entity.paperwork.CashPurchase;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class PaperworkBreakdownResponse {

    private Long id;

    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paperworkDate;
    @NotNull
    private BigDecimal merchantSale;
    private BigDecimal salesTax;
    private BigDecimal insideSales;
    private BigDecimal totalSalesRecord; // merchantSale + salesTax + insideSales + totalSalesRecord

    private BigDecimal creditCard;
    private BigDecimal debitCard;
    private BigDecimal totalCreditDebitCard; // creditCard + debitCard
    private BigDecimal ebt;
    private BigDecimal expense;
    private BigDecimal officeExpense;
    private BigDecimal trustFund;
    private BigDecimal houseAc;
    private BigDecimal storeDeposit;
    private BigDecimal totalInsideSalesBreakdown;
    private BigDecimal totalCashPurchase; // This will come from CashPurchase entity
    private BigDecimal totalInsideSales; // totalCreditDebitCard + ebt + expense + officeExpense + trustFund + houseAc + storeDeposit+ totalCashPurchase
    private BigDecimal cashOverShort;
    private String notes;

    private Long paperworksId;
    private List<CashPurchaseDto> cashPurchaseList;
//    private List<CheckPurchase> checkPurchaseList;

    public PaperworkBreakdown to(PaperworkBreakdown paperworkBreakdown) {
        paperworkBreakdown.setPaperworkDate(paperworkDate);
        paperworkBreakdown.setMerchantSale(Objects.nonNull(merchantSale) ? merchantSale : BigDecimal.ZERO);
        paperworkBreakdown.setInsideSales(Objects.nonNull(insideSales) ? insideSales : BigDecimal.ZERO);
        paperworkBreakdown.setSalesTax(Objects.nonNull(salesTax) ? salesTax : BigDecimal.ZERO);
        paperworkBreakdown.setTotalSalesRecord(Objects.nonNull(totalSalesRecord) ? totalSalesRecord : BigDecimal.ZERO);
        paperworkBreakdown.setCreditCard(Objects.nonNull(creditCard) ? creditCard : BigDecimal.ZERO);
        paperworkBreakdown.setDebitCard(Objects.nonNull(debitCard) ? debitCard : BigDecimal.ZERO);
        paperworkBreakdown.setTotalCreditDebitCard(Objects.nonNull(totalCreditDebitCard) ? totalCreditDebitCard : BigDecimal.ZERO);
        paperworkBreakdown.setEbt(Objects.nonNull(ebt) ? ebt : BigDecimal.ZERO);
        paperworkBreakdown.setExpense(Objects.nonNull(expense) ? expense : BigDecimal.ZERO);
        paperworkBreakdown.setOfficeExpense(Objects.nonNull(officeExpense) ? officeExpense : BigDecimal.ZERO);
        paperworkBreakdown.setTrustFund(Objects.nonNull(trustFund) ? trustFund : BigDecimal.ZERO);
        paperworkBreakdown.setHouseAc(Objects.nonNull(houseAc) ? houseAc : BigDecimal.ZERO);
        paperworkBreakdown.setStoreDeposit(Objects.nonNull(storeDeposit) ? storeDeposit : BigDecimal.ZERO);
        paperworkBreakdown.setTotalInsideSales(Objects.nonNull(totalInsideSales) ? totalInsideSales : BigDecimal.ZERO);
        paperworkBreakdown.setTotalCashPurchase(Objects.nonNull(totalCashPurchase) ? totalCashPurchase : BigDecimal.ZERO);
        paperworkBreakdown.setNotes(notes);
        paperworkBreakdown.setTotalInsideSalesBreakdown(totalInsideSalesBreakdown);
        paperworkBreakdown.setCashOverShort(Objects.nonNull(cashOverShort) ? cashOverShort : BigDecimal.ZERO);

        setCashPurchase(paperworkBreakdown);

        return paperworkBreakdown;
    }

    private void setCashPurchase(PaperworkBreakdown paperworkBreakdown) {
        List<CashPurchase> cashPurchases = new ArrayList<>();
        if (Objects.nonNull(cashPurchaseList)) {
            if (Objects.nonNull(paperworkBreakdown.getCashPurchaseList()))
                paperworkBreakdown.getCashPurchaseList().clear();
            cashPurchaseList.forEach(data -> {
                CashPurchase cashPurchase = new CashPurchase();
                CashPurchaseDto.to(cashPurchase, data);
                cashPurchase.setPaperworkBreakdown(paperworkBreakdown);
                cashPurchases.add(cashPurchase);
            });
            paperworkBreakdown.setCashPurchaseList(cashPurchases);
        }
    }

    public static PaperworkBreakdownResponse from(PaperworkBreakdown paperworkBreakdown) {
        PaperworkBreakdownResponse response = new PaperworkBreakdownResponse();
        BeanUtils.copyProperties(paperworkBreakdown, response);
        List<CashPurchaseDto> cashPurchaseDtos = new ArrayList<>();
        if (Objects.nonNull(paperworkBreakdown.getCashPurchaseList())) {
            paperworkBreakdown.getCashPurchaseList().forEach(data -> {
                cashPurchaseDtos.add(CashPurchaseDto.from(data));
            });
            response.setCashPurchaseList(cashPurchaseDtos);
        }
        return response;
    }

    public static PaperworkBreakdownResponse from(LocalDate paperworkBreakdownDate) {
        PaperworkBreakdownResponse response = new PaperworkBreakdownResponse();
        response.setPaperworkDate(paperworkBreakdownDate);
        return response;
    }
}
