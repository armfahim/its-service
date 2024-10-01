package com.its.service.service.impl.paperworks;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.paperworks.PaperworksDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.entity.paperwork.Paperworks;
import com.its.service.enums.Month;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.paperworks.PaperworksRepository;
import com.its.service.response.PaperworksDetailsResponse;
import com.its.service.service.paperworks.PaperworksService;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaperworksServiceImpl implements PaperworksService {

    private final PaperworksRepository repository;


    @Override
    public Paperworks save(Paperworks paperworks) {
        BasicAudit.setAttributeForCreateUpdate(paperworks, false, RecordStatus.ACTIVE);
        paperworks.setPaperworkTitle(generatePaperworkTitle(paperworks));
        return repository.save(paperworks);
    }

    private String generatePaperworkTitle(Paperworks paperworks) {
        return paperworks.getYear() + "-" + paperworks.getMonth() + "-" + "Paperwork";
    }


    @Override
    public Paperworks update(Paperworks paperworks) {
        return null;
    }

    @Override
    public Paperworks findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlreadyExistsException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(Paperworks paperworks) {

    }

    @Override
    public List<Paperworks> findAll() {
        return List.of();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        return null;
    }

    @Override
    public PaginatedResponse listAndSearch(String sort, String dir, Integer page, Integer size, String year, String month, String paperworkTitle) {
        sort = sort.isEmpty() ? "year" : sort;
        dir = dir.isEmpty() ? "desc" : dir;
        year = year.equals("") ? null : year;
        month = month.equals("") ? null : month;
        paperworkTitle = paperworkTitle.equals("") ? null : paperworkTitle;

        Page<Paperworks> pageData = repository.findListAndSearch(year, month, paperworkTitle, PaginationUtils.getPageable(sort, dir, page, size));

        List<PaperworksDto> data = pageData.getContent().stream().map(PaperworksDto::from).toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public PaperworksDetailsResponse findPaperworkAndBreakdownDatesById(Long id) {
        Paperworks paperworks = this.findById(id);
        PaperworksDetailsResponse response = PaperworksDetailsResponse.from(paperworks);

        //Get the exists date list from paperwork breakdown dates
        List<LocalDate> existsDate = new ArrayList<>();
        if (paperworks.getPaperworkBreakdownList() != null
                && !paperworks.getPaperworkBreakdownList().isEmpty()) {

            existsDate = paperworks.getPaperworkBreakdownList().stream()
                    .map(PaperworkBreakdown::getPaperworkDate)
                    .toList();
            response.setExistsPaperworkBreakDownDates(existsDate); //Set to response
        }
        //Get the new input date by comparing if exists or get the first day of month
        if (!existsDate.isEmpty()) {
            Optional<LocalDate> latestDate = existsDate.stream().max(LocalDate::compareTo);
            latestDate.ifPresent(localDate -> response.setNewInputDate(localDate.plusDays(1)));
        } else {
            String monthNumber = Month.valueOf(paperworks.getMonth()).getDisplayName(); // Get the month number from Month enum
            LocalDate firstDay = LocalDate.of(Integer.parseInt(paperworks.getYear())
                    , Integer.parseInt(monthNumber), 1); // Get the first day of paperwork year and moth
            response.setNewInputDate(firstDay);
        }
        return response;
    }

    @Override
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        Optional<Paperworks> paperworks = repository.findById(id);
        paperworks.ifPresent(repository::delete);
    }
}
