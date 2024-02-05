package com.its.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.NumberUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class InvoiceDetailsViewDto {
    private Long id;
    private String invoiceNumber;
    private String invoiceDesc;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate invoiceDate;
    private String term;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paymentDueDate;
    private String invoiceAmount;
    private String creditAmount;
    private String netDue;
    private String chequeNumber;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paidDate;
    private Boolean isPaid;
    private SupplierDetailsDto supplierDetailsDto;

    public static InvoiceDetailsViewDto from(InvoiceDetails invoiceDetails) {
        InvoiceDetailsViewDto dto = new InvoiceDetailsViewDto();
        BeanUtils.copyProperties(invoiceDetails, dto);
        dto.setTerm(invoiceDetails.getTerm().getDisplayName());
        dto.setIsPaid(Objects.nonNull(invoiceDetails.getPaidDate()) ? true : false);
        dto.setChequeNumber(StringUtils.isNotEmpty(invoiceDetails.getChequeNumber()) ? invoiceDetails.getChequeNumber() : null);
        dto.setInvoiceAmount(NumberUtils.getRoundOffValue(invoiceDetails.getInvoiceAmount()));
        dto.setCreditAmount(NumberUtils.getRoundOffValue(invoiceDetails.getCreditAmount()));
        dto.setNetDue(NumberUtils.getRoundOffValue(invoiceDetails.getNetDue()));
        dto.setSupplierDetailsDto(SupplierDetailsDto.from(invoiceDetails.getSupplierDetails()));
        return dto;
    }

}
