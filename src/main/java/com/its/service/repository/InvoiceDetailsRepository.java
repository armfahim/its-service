package com.its.service.repository;

import com.its.service.entity.InvoiceDetails;
import com.its.service.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Query(value = "SELECT i FROM InvoiceDetails i " +
            "JOIN SupplierDetails s ON s.id = i.supplierDetails.id " +
            "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
            "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
            "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate) " +
            "AND (i.recordStatus = 'ACTIVE') ",
            countQuery = "SELECT COUNT(i) FROM InvoiceDetails i " +
                    "JOIN SupplierDetails s ON s.id = i.supplierDetails.id " +
                    "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
                    "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
                    "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate)" +
                    "AND (i.recordStatus = 'ACTIVE') ",
            nativeQuery = false)
    Page<InvoiceDetails> findByListAndSearch(Long supplierId, LocalDate fromInvoiceDate, LocalDate toInvoiceDate, Pageable pageable);

    List<InvoiceDetails> findAllByRecordStatus(RecordStatus recordStatus);

    List<InvoiceDetails> findAllByRecordStatusAndIsPaidFalse(RecordStatus recordStatus);
}
