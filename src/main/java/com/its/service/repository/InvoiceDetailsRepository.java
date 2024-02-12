package com.its.service.repository;

import com.its.service.entity.InvoiceDetails;
import com.its.service.enums.RecordStatus;
import com.its.service.enums.Term;
import com.its.service.projection.InvoiceTotalAmountProjection;
import com.its.service.projection.MonthlyInvoiceTotalAmountProjection;
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

    @Query(value = """
            SELECT
            	SUM(invoice_amount) as totalPurchase
            FROM
            	invoice_details id
            JOIN
                supplier_details sd ON id.supplier_id = sd.id
            WHERE
            	id.record_status = 'ACTIVE'
            	AND sd.record_status = 'ACTIVE'
            	AND (:supplierId IS NULL or sd.id = :supplierId)
            """
            ,nativeQuery = true)
    InvoiceTotalAmountProjection findPurchaseAmountBySupplier(Long supplierId);

    @Query(value = """
            SELECT
            	MONTH(invoice_date) AS month,
            	SUM(invoice_amount) AS monthlyTotal
            FROM
            	invoice_details id
            JOIN
                supplier_details sd ON id.supplier_id = sd.id
            WHERE
            	(:year IS NULL OR YEAR(id.invoice_date) = :year)
            	AND (:supplierId IS NULL OR sd.id = :supplierId)
            	AND id.record_status = 'ACTIVE'
            	AND sd.record_status = 'ACTIVE'
            GROUP BY
            	MONTH(id.invoice_date)
            ORDER BY
            	MONTH(id.invoice_date)
            """
            ,nativeQuery = true)
    MonthlyInvoiceTotalAmountProjection findPurchaseAmountBySupplierOrYearInMonth(String year, Long supplierId);
}
