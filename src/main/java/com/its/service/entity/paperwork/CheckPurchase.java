package com.its.service.entity.paperwork;

import com.its.service.entity.BaseEntity;
import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.SupplierDetails;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "CHECK_PURCHASE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckPurchase extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPERWORK_BREAKDOWN_ID")
    private PaperworkBreakdown paperworkBreakdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_DETAILS_ID")
    private InvoiceDetails invoiceDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIERS_ID")
    private SupplierDetails supplierDetails;
}
