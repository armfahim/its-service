package com.its.service.service.paperworks;

import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.entity.paperwork.Paperworks;
import com.its.service.request.PaperworkBreakdownRequest;
import com.its.service.service.GenericService;

import java.awt.print.Paper;
import java.time.LocalDate;

public interface PaperworkBreakdownService extends GenericService<PaperworkBreakdown> {

    PaperworkBreakdown save(PaperworkBreakdownDto dto);

    PaperworkBreakdown update(PaperworkBreakdownDto dto);

    Boolean existsByPaperworkBreakdownDate(LocalDate paperworkDate);

    PaperworkBreakdown findPaperworkBreakdownByDateAndId(Long paperworksId, LocalDate paperworkBreakdownDate);
}
