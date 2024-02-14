package com.its.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.entity.InvoiceDetails;
import com.its.service.utils.NumberUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
public class PaymentStatusDto {

    @NotNull
    private Long id;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate paidDate;
    private String chequeNumber;
    private Boolean isPaid;
    private String invoiceDesc;

    public static PaymentStatusDto from(InvoiceDetails invoiceDetails) {
        PaymentStatusDto dto = new PaymentStatusDto();
        BeanUtils.copyProperties(invoiceDetails, dto);
        return dto;
    }

    public void to(InvoiceDetails invoiceDetails) {
        invoiceDetails.setPaidDate(paidDate);
        invoiceDetails.setIsPaid(Objects.isNull(isPaid) ? Boolean.FALSE : isPaid);
        if (Objects.nonNull(isPaid) && Boolean.FALSE.equals(isPaid)) {
            invoiceDetails.setPaidDate(null);
        } else {
            invoiceDetails.setPaidDate(paidDate);
        }
        invoiceDetails.setChequeNumber(Objects.nonNull(chequeNumber) ? chequeNumber.trim() : null);
        invoiceDetails.setInvoiceDesc(Objects.nonNull(invoiceDesc) ? invoiceDesc.trim() : null);
    }
}
