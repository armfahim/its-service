package com.its.service.repository;

import com.its.service.entity.SupplierDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long> {

    @Query(value = "SELECT sd FROM SupplierDetails sd WHERE " +
            "(:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%')))",
            countQuery = "SELECT COUNT(sd) FROM SupplierDetails sd WHERE " +
                    "(:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%')))")
    Page<SupplierDetails> findByListAndSearch(String supplierName, Pageable pageable);

}
