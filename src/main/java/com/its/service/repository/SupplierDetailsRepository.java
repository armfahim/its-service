package com.its.service.repository;

import com.its.service.entity.SupplierDetails;
import com.its.service.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long> {

    @Query(value = "SELECT sd FROM SupplierDetails sd WHERE " +
            "(:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%'))) " +
            "AND (sd.recordStatus = 'ACTIVE') ",
            countQuery = "SELECT COUNT(sd) FROM SupplierDetails sd WHERE " +
                    "(:supplierName IS NULL OR LOWER(sd.supplierName) LIKE LOWER(CONCAT('%', :supplierName, '%'))) " +
                    "AND (sd.recordStatus = 'ACTIVE') ")
    Page<SupplierDetails> findByListAndSearch(String supplierName, Pageable pageable);

    List<SupplierDetails> findAllByRecordStatus(RecordStatus status);

    Optional<SupplierDetails> findByEmailAndRecordStatus(String email,RecordStatus recordStatus);

    Optional<SupplierDetails> findByPhoneAndRecordStatus(String phone,RecordStatus recordStatus);

}
