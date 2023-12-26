package com.its.service.repository;

import com.its.service.entity.InvoiceDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Query(value = """
            SELECT *
                 FROM invoice_details i
                 INNER JOIN supplier_details s ON s.id = i.supplier_id
                 WHERE\s
                     (:supplierId IS NULL OR s.id = :supplierId)
                     AND (
                         :fromInvoiceDate IS NULL\s
                         OR (i.invoice_date >= :fromInvoiceDate AND (:toInvoiceDate IS NULL OR i.invoice_date <= :toInvoiceDate))
                     )
            """,nativeQuery = true)
    Page<InvoiceDetails> findByListAndSearch(Long supplierId, String fromInvoiceDate, String toInvoiceDate, Pageable pageable);
}
