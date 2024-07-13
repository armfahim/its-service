package com.its.service.repository.paperworks;

import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.entity.paperwork.Paperworks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PaperworkBreakdownRepository extends JpaRepository<PaperworkBreakdown, Long> {
    Optional<PaperworkBreakdown> findByPaperworkDate(LocalDate paperworkDate);

    Optional<PaperworkBreakdown> findByPaperworksAndPaperworkDate(Paperworks paperworks, LocalDate paperworkBreakdownDate);
}
