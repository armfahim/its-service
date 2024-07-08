package com.its.service.entity.paperwork;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.entity.BaseEntity;
import com.its.service.entity.SupplierDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PAPERWORK_BREAKDOWN")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaperworkBreakdown extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "PAPERWORK_DATE", nullable = false, unique = true)
    private LocalDate paperworkDate;

    //Start - Sales Record(Incoming)
    @Column(name = "MERCHANT_SALE", precision = 12, scale = 2)
    private BigDecimal merchantSale;

    @Column(name = "SALES_TAX", precision = 12, scale = 2)
    private BigDecimal salesTax;

    @Column(name = "INSIDE_SALES", precision = 12, scale = 2)
    private BigDecimal insideSales;

    @Column(name = "TOTAL_SALES_RECORD", precision = 12, scale = 2)
    private BigDecimal totalSalesRecord; // merchantSale + salesTax + insideSales + totalSalesRecord
    //End

    //Start - Inside Sales Breakdown
    @Column(name = "CREDIT_CARD", precision = 12, scale = 2)
    private BigDecimal creditCard;

    @Column(name = "DEBIT_CARD", precision = 12, scale = 2)
    private BigDecimal debitCard;

    @Column(name = "TOTAL_CREDIT_DEBIT_CARD", precision = 12, scale = 2)
    private BigDecimal totalCreditDebitCard; // creditCard + debitCard

    @Column(name = "EBT", precision = 12, scale = 2)
    private BigDecimal ebt;

    @Column(name = "EXPENSE", precision = 12, scale = 2)
    private BigDecimal expense;

    @Column(name = "OFFICE_EXPENSE", precision = 12, scale = 2)
    private BigDecimal officeExpense;

    @Column(name = "TRUST_FUND", precision = 12, scale = 2)
    private BigDecimal trustFund;

    @Column(name = "HOUSE_AC", precision = 12, scale = 2)
    private BigDecimal houseAc;

    @Column(name = "STORE_DEPOSIT", precision = 12, scale = 2)
    private BigDecimal storeDeposit;

    @Column(name = "TOTAL", precision = 12, scale = 2)
    private BigDecimal total; // totalCreditDebitCard + ebt + expense + officeExpense + trustFund + houseAc + storeDeposit
    //End

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPERWORKS_ID")
    private Paperworks paperworks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paperworkBreakdown", cascade = CascadeType.ALL)
    private List<CashPurchase> cashPurchaseList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paperworkBreakdown", cascade = CascadeType.ALL)
    private List<CheckPurchase> checkPurchaseList;

}
