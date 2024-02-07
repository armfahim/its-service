package com.its.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.service.enums.Term;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "INVOICE_DETAILS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetails extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INVOICE_NUMBER", nullable = false)
    private String invoiceNumber;

    @Column(name = "INVOICE_DESC", columnDefinition = "varchar(MAX)")
    private String invoiceDesc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "INVOICE_DATE", nullable = false)
    private LocalDate invoiceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TERM", length = 16, nullable = false)
    private Term term;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "PAYMENT_DUE_DATE")
    private LocalDate paymentDueDate; // invoiceDate + term (days)

    @Column(name = "INVOICE_AMOUNT", nullable = false, precision = 10, scale = 2)
    // Example: 10 digits in total, with 2 digits after the decimal point
    private BigDecimal invoiceAmount;

    @Column(name = "CREDIT_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal creditAmount;

    @Column(name = "NET_DUE", nullable = false, precision = 10, scale = 2)
    private BigDecimal netDue; // (invoiceAmount - creditAmount)

    @Column(name = "CHEQUE_NUMBER", columnDefinition = "varchar(255)")
    private String chequeNumber;

    @Column(name = "IS_PAID")
    private Boolean isPaid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "PAID_DATE")
    private LocalDate paidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID")
    private SupplierDetails supplierDetails;

    public void setTermByValue(String targetValue) {
        this.term = Arrays.stream(Term.values())
                .filter(enumTerm -> enumTerm.getDisplayName().equals(targetValue))
                .findFirst()
                .orElse(null);
    }
}
