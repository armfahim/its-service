package com.its.service.repository;

import com.its.service.entity.SupplierDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long> {

    @Query(value = "SELECT sd FROM SupplierDetails sd " +
            "WHERE (:supplierId IS NULL OR LOWER(sd.supplierID) LIKE LOWER(CONCAT('%', :supplierId, '%'))) " +
            "AND (:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%')))",
            countQuery = "SELECT COUNT(sd) FROM SupplierDetails sd " +
                    "WHERE (:supplierId IS NULL OR LOWER(sd.supplierID) LIKE LOWER(CONCAT('%', :supplierId, '%'))) " +
                    "AND (:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%')))")
    Page<SupplierDetails> findByListAndSearch(String supplierId, String supplierName, Pageable pageable);

    Page<SupplierDetails> findBySupplierIDContainingIgnoreCaseAndSupplierNameContainingIgnoreCase(String supplierId, String supplierName, Pageable pageable);
}
