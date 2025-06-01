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
            "JOIN ShopBranch sb ON sb.id = i.shopBranch.id " +
            "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
            "AND (:branchId IS NULL OR sb.id = :branchId) " +
            "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
            "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate) " +
            "AND (i.recordStatus = 'ACTIVE') " +
            "AND (s.recordStatus = 'ACTIVE') ",
            countQuery = "SELECT COUNT(i) FROM InvoiceDetails i " +
                    "JOIN SupplierDetails s ON s.id = i.supplierDetails.id " +
                    "JOIN ShopBranch sb ON sb.id = i.shopBranch.id " +
                    "WHERE (:supplierId IS NULL OR s.id = :supplierId) " +
                    "AND (:branchId IS NULL OR sb.id = :branchId) " +
                    "AND (:fromInvoiceDate IS NULL OR i.invoiceDate >= :fromInvoiceDate) " +
                    "AND (:toInvoiceDate IS NULL OR i.invoiceDate <= :toInvoiceDate)" +
                    "AND (i.recordStatus = 'ACTIVE') " +
                    "AND (s.recordStatus = 'ACTIVE') ",
            nativeQuery = false)
    Page<InvoiceDetails> findByListAndSearch(Long supplierId, Long branchId, LocalDate fromInvoiceDate, LocalDate toInvoiceDate, Pageable pageable);

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
                SUM(invoice_amount) AS totalPurchase
            FROM
                invoice_details ids
            INNER JOIN
                supplier_details sd ON ids.supplier_id = sd.id
            INNER JOIN
                shop_branch shb ON ids.shop_branch_id = shb.id
            WHERE
                ids.record_status = 'ACTIVE'
                AND sd.record_status = 'ACTIVE'
                AND ids.supplier_id = ISNULL(:supplierId, ids.supplier_id)
                AND ids.shop_branch_id = ISNULL(:branchId, ids.shop_branch_id)
            """
            , nativeQuery = true)
    InvoiceTotalAmountProjection findPurchaseAmountBySupplier(Long supplierId, Long branchId);

    @Query(value = """
                SELECT
                	MONTH(invoice_date) AS month,
                	SUM(invoice_amount) AS monthlyTotal
                FROM
                	invoice_details id
                INNER JOIN
                    supplier_details sd ON id.supplier_id = sd.id
                INNER JOIN
            		shop_branch shb ON id.shop_branch_id = shb.id
                WHERE
                	YEAR(id.invoice_date) = ISNULL(:year, YEAR(id.invoice_date))
                	AND id.supplier_id = ISNULL(:supplierId, id.supplier_id)
                	AND id.shop_branch_id = ISNULL(:branchId, id.shop_branch_id)
                	AND id.record_status = 'ACTIVE'
                	AND sd.record_status = 'ACTIVE'
                GROUP BY
                	MONTH(id.invoice_date)
                ORDER BY
                	MONTH(id.invoice_date)
            """
            , nativeQuery = true)
    List<MonthlyInvoiceTotalAmountProjection> findPurchaseAmountBySupplierOrYearInMonth(String year, Long supplierId, Long branchId);

    List<InvoiceDetails> findBySupplierDetailsIdAndRecordStatus(Long id, RecordStatus recordStatus);

    List<InvoiceDetails> findByShopBranchIdAndRecordStatus(Long branchId, RecordStatus recordStatus);

    List<InvoiceDetails> findBySupplierDetailsIdAndShopBranchIdAndRecordStatus(Long supplierId, Long branchId, RecordStatus recordStatus);
}
