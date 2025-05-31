package com.its.service.repository;

import com.its.service.entity.ShopBranch;
import com.its.service.entity.SupplierDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopBranchRepository extends JpaRepository<ShopBranch, Long> {

    Page<ShopBranch> findByBranchNameContainingIgnoreCase(String shopBranchName, Pageable pageable);
}
