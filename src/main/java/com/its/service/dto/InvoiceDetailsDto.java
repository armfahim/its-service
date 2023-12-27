package com.its.service.dto;

import com.its.service.entity.InvoiceDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
public class InvoiceDetailsDto {

    private Long id;
    private String invoiceNumber;
    private String invoiceDesc;
    private LocalDate invoiceDate;
    private String term;
    private LocalDate paymentDueDate;
    private BigDecimal invoiceAmount;
    private BigDecimal creditAmount;
    private BigDecimal netDue;
    private String chequeNumber;
    private LocalDate paidDate;
    private Long supplierDetails;
    private String supplierName;

    public static InvoiceDetailsDto from(InvoiceDetails invoiceDetails) {
        InvoiceDetailsDto dto = new InvoiceDetailsDto();
        if (Objects.nonNull(invoiceDetails.getSupplierDetails())) {
            dto.setSupplierDetails(invoiceDetails.getSupplierDetails().getId());
            dto.setSupplierName(invoiceDetails.getSupplierDetails().getSupplierName());
        }
        if (Objects.nonNull(invoiceDetails.getTerm()))
            dto.setTerm(invoiceDetails.getTerm().getDisplayName());
        BeanUtils.copyProperties(invoiceDetails, dto);
        return dto;
    }

    public void to(InvoiceDetails invoiceDetails) {
        invoiceDetails.setInvoiceNumber(invoiceNumber);
        invoiceDetails.setInvoiceDesc(invoiceDesc);
        invoiceDetails.setInvoiceDate(invoiceDate);
        invoiceDetails.setInvoiceAmount(invoiceAmount);
        invoiceDetails.setChequeNumber(chequeNumber);
//        invoiceDetails.setSupplierDetails(supplierDetails);
        invoiceDetails.setCreditAmount(creditAmount);
        invoiceDetails.setNetDue(netDue);
        invoiceDetails.setPaidDate(paidDate);
        invoiceDetails.setTermByValue(term);
        invoiceDetails.setPaymentDueDate(paymentDueDate);
    }
}
