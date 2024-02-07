package com.its.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.entity.InvoiceDetails;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.utils.NumberUtils;
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
    private String invoiceAmount;
    private String creditAmount;
    private String netDue;
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
        dto.setInvoiceAmount(NumberUtils.getRoundOffValue(invoiceDetails.getInvoiceAmount()));
        dto.setCreditAmount(NumberUtils.getRoundOffValue(invoiceDetails.getCreditAmount()));
        dto.setNetDue(NumberUtils.getRoundOffValue(invoiceDetails.getNetDue()));
        return dto;
    }

    public void to(InvoiceDetails invoiceDetails) {
        if(invoiceAmount.length() > 10) throw new AlreadyExistsException("Invoice amount is allowed upto 10 digits");
        if(creditAmount.length() > 10) throw new AlreadyExistsException("Credit amount is allowed upto 10 digits");
        if(netDue.length() > 10) throw new AlreadyExistsException("Net due amount is allowed upto 10 digits");
        invoiceDetails.setInvoiceNumber(invoiceNumber);
        invoiceDetails.setInvoiceDesc(Objects.nonNull(invoiceDesc) ? invoiceDesc.trim() : null);
        invoiceDetails.setInvoiceDate(invoiceDate);
        invoiceDetails.setInvoiceAmount(new BigDecimal(invoiceAmount));
        invoiceDetails.setChequeNumber(Objects.nonNull(chequeNumber) ? chequeNumber.trim() : null);
        invoiceDetails.setCreditAmount(new BigDecimal(creditAmount));
        invoiceDetails.setNetDue(new BigDecimal(netDue));
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
