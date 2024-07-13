package com.its.service.entity.paperwork;

import com.its.service.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "CASH_PURCHASE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CashPurchase extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PURCHASE_AMOUNT", precision = 12, scale = 2)
    private BigDecimal cashPurchaseAmount;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPERWORK_BREAKDOWN_ID")
    private PaperworkBreakdown paperworkBreakdown;
}
