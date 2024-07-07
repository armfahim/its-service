package com.its.service.repository.paperworks;

import com.its.service.entity.InvoiceDetails;
import com.its.service.entity.paperwork.Paperworks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaperworksRepository extends JpaRepository<Paperworks, Long> {

    @Query(value = "SELECT x FROM Paperworks x " +
            "WHERE (:year IS NULL OR x.year = :year) " +
            "AND (:month IS NULL OR LOWER(x.month) LIKE LOWER(CONCAT('%', :month, '%'))) " +
            "AND (:paperworkTitle IS NULL OR LOWER(x.paperworkTitle) LIKE LOWER(CONCAT('%', :paperworkTitle, '%'))) " +
            "AND (x.recordStatus = 'ACTIVE') "
            , countQuery = "SELECT  COUNT(x) FROM Paperworks x " +
            "WHERE (:year IS NULL OR x.year = :year) " +
            "AND (:month IS NULL OR LOWER(x.month) LIKE LOWER(CONCAT('%', :month, '%'))) " +
            "AND (:paperworkTitle IS NULL OR LOWER(x.paperworkTitle) LIKE LOWER(CONCAT('%', :paperworkTitle, '%'))) " +
            "AND (x.recordStatus = 'ACTIVE') "
            ,nativeQuery = false)
    Page<Paperworks> findListAndSearch(String year, String month, String paperworkTitle, Pageable pageable);
}
