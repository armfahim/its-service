package com.its.service.repository.paperworks;

import com.its.service.entity.paperwork.CashPurchase;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashPurchaseRepository extends JpaRepository<CashPurchase, Long> {
    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM cash_purchase 
            WHERE paperwork_breakdown_id = :paperworkBreakdownId
            """,nativeQuery = true)
    void deleteByPaperworkBreakdown(Long paperworkBreakdownId);
}
