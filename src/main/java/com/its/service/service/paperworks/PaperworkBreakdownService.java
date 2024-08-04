package com.its.service.service.paperworks;

import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.service.GenericService;

import java.time.LocalDate;
import java.util.List;

public interface PaperworkBreakdownService extends GenericService<PaperworkBreakdown> {

    PaperworkBreakdown save(PaperworkBreakdownDto dto);

    PaperworkBreakdown update(PaperworkBreakdownDto dto);

    Boolean existsByPaperworkBreakdownDate(LocalDate paperworkDate);

    PaperworkBreakdown findPaperworkBreakdownByDateAndId(Long paperworksId, LocalDate paperworkBreakdownDate);

    List<PaperworkBreakdown> findAllPaperworkBreakdownByPaperworkId(Long paperworksId);
}
