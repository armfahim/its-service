package com.its.service.service.paperworks;

import com.its.service.entity.paperwork.Paperworks;
import com.its.service.response.PaperworksDetailsResponse;
import com.its.service.service.GenericService;
import com.its.service.utils.PaginatedResponse;

public interface PaperworksService extends GenericService<Paperworks> {

    PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, String year, String month, String paperworkTitle);

    PaperworksDetailsResponse findPaperworkAndBreakdownDatesById(Long id);
}
