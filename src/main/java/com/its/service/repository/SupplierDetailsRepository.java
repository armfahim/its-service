package com.its.service.repository;

import com.its.service.entity.SupplierDetails;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long> {
}
