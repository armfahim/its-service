package com.its.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate invoiceDate;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paymentDueDate;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paidDate;

    private String term;
    private BigDecimal invoiceAmount;
    private BigDecimal creditAmount;
    private BigDecimal netDue;
    private String chequeNumber;
    private Boolean isPaid;
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
        invoiceDetails.setInvoiceDesc(Objects.nonNull(invoiceDesc) ? invoiceDesc.trim() : null);
        invoiceDetails.setInvoiceDate(invoiceDate);
        invoiceDetails.setInvoiceAmount(invoiceAmount);
        invoiceDetails.setChequeNumber(Objects.nonNull(chequeNumber) ? chequeNumber.trim() : null);
        invoiceDetails.setCreditAmount(creditAmount);
        invoiceDetails.setNetDue(netDue);
        invoiceDetails.setTermByValue(term);
        invoiceDetails.setPaymentDueDate(paymentDueDate);
        invoiceDetails.setIsPaid(Objects.isNull(isPaid) ? Boolean.FALSE : isPaid);
        if (Objects.nonNull(isPaid) && Boolean.FALSE.equals(isPaid)) {
            invoiceDetails.setPaidDate(null);
        } else {
            invoiceDetails.setPaidDate(paidDate);
        }
    }
}
