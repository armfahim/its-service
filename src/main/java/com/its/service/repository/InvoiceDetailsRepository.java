package com.its.service.repository;

import com.its.service.entity.InvoiceDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.enums.Term;
import com.its.service.projection.InvoiceTotalAmountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Query(value = "SELECT i FROM InvoiceDetails i " +
            "JOIN SupplierDetails s ON s.id = i.supplierDetails.id " +
            "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
            "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
            "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate) " +
            "AND (i.recordStatus = 'ACTIVE') " +
            "AND (s.recordStatus = 'ACTIVE') ",
            countQuery = "SELECT COUNT(i) FROM InvoiceDetails i " +
                    "JOIN SupplierDetails s ON s.id = i.supplierDetails.id " +
                    "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
                    "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
                    "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate)" +
                    "AND (i.recordStatus = 'ACTIVE') " +
                    "AND (s.recordStatus = 'ACTIVE') ",
            nativeQuery = false)
    Page<InvoiceDetails> findByListAndSearch(Long supplierId, LocalDate fromInvoiceDate, LocalDate toInvoiceDate, Pageable pageable);

    List<InvoiceDetails> findAllByRecordStatusAndSupplierDetailsRecordStatus(RecordStatus recordStatus, RecordStatus recordStatuss);

    List<InvoiceDetails> findAllByRecordStatusAndIsPaidFalseAndTermNotAndSupplierDetailsRecordStatus(RecordStatus recordStatus, Term term, RecordStatus recordStatuss);

    Optional<InvoiceDetails> findByInvoiceNumberAndRecordStatus(String invoiceNumber, RecordStatus recordStatus);

    @Query(value = """
            SELECT
            SUM(invoice_amount) as totalPurchase
            FROM invoice_details id
            WHERE
            id.record_status = 'ACTIVE' AND
            (:year IS NULL or YEAR(id.invoice_date) = :year)
            AND
            (:month IS NULL or MONTH(id.invoice_date) = :month)
            """, nativeQuery = true)
    InvoiceTotalAmountProjection findInvoiceTotalByYearAndMonth(String year, String month);

    @Query(value = """
            SELECT DISTINCT YEAR(invoice_date) AS year
            FROM invoice_details
            """, nativeQuery = true)
    List<String> findDistinctInvoiceYearByInvoiceDate();
}
