package com.its.service.dto;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import com.its.service.enums.Term;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InvoiceDetailsDto {

    private Long id;
    private String invoiceNumber;
    private String invoiceDesc;
    private LocalDate invoiceDate;
    private Term term;
    private LocalDate paymentDueDate;
    private BigDecimal invoiceAmount;
    private BigDecimal creditAmount;
    private BigDecimal netDue;
    private String chequeNumber;
    private LocalDate paidDate;
    private SupplierDetails supplierDetails;

    public static InvoiceDetailsDto from(InvoiceDetails invoiceDetails) {
        InvoiceDetailsDto dto = new InvoiceDetailsDto();
        BeanUtils.copyProperties(invoiceDetails, dto);
        return dto;
    }

    public void to(InvoiceDetails invoiceDetails) {
        invoiceDetails.setInvoiceNumber(invoiceNumber);
        invoiceDetails.setInvoiceDesc(invoiceDesc);
        invoiceDetails.setInvoiceDate(invoiceDate);
        invoiceDetails.setInvoiceAmount(invoiceAmount);
        invoiceDetails.setChequeNumber(chequeNumber);
        invoiceDetails.setSupplierDetails(supplierDetails);
        invoiceDetails.setCreditAmount(creditAmount);
        invoiceDetails.setNetDue(netDue);
        invoiceDetails.setPaidDate(paidDate);
        invoiceDetails.setTerm(term);
        invoiceDetails.setPaymentDueDate(paymentDueDate);
    }
}
