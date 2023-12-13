package com.its.service.repository;

import com.its.service.entity.SupplierDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long> {

    @Query(value = """
            SELECT * FROM supplier_details sd 
            WHERE 
            (:supplierId IS NULL OR sd.supplier_id = :supplierId) AND 
            (:supplierName IS NULL OR sd.supplier_name = :supplierName)
            """,
            countQuery = """
            SELECT COUNT(sd.supplier_id)
            FROM supplier_details sd 
            WHERE
            (:supplierId IS NULL OR sd.supplier_id = :supplierId) AND
            (:supplierName IS NULL OR sd.supplier_name = :supplierName)
            """,
            nativeQuery = true)
    Page<SupplierDetails> findByListAndSearch(String supplierId, String supplierName, Pageable pageable);

    Page<SupplierDetails> findBySupplierIDContainingIgnoreCaseAndSupplierNameContainingIgnoreCase(String supplierId, String supplierName, Pageable pageable);
}
