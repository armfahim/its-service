package com.its.service.resource.paperworks;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.service.paperworks.PaperworkBreakdownService;
import com.its.service.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/paperwork/breakdown")
@RequiredArgsConstructor
@Slf4j
public class PaperworkBreakdownResource {

    private final PaperworkBreakdownService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody PaperworkBreakdownDto dto) {
        if (service.existsByPaperworkBreakdownDate(dto.getPaperworkDate()))
            throw new AlreadyExistsException("Data already exists by this date");
        return ok(success(PaperworkBreakdownDto.from(service.save(dto)), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody PaperworkBreakdownDto dto) {
        if (Objects.isNull(dto.getId())) {
            throw new AlreadyExistsException("Paperwork breakdown ID is missing");
        }
        return ok(success(PaperworkBreakdownDto.from(service.update(dto)), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @GetMapping(value = "/find/paperwork-breakdown-date")
    public ResponseEntity<Object> findPaperworkBreakdownByDateAndId(@RequestParam(value = "paperworksId") Long paperworksId,
                                                                    @RequestParam(value = "paperworkBreakdownDate") String paperworkBreakdownDate) {
        if (Objects.isNull(paperworksId)) return new ResponseEntity<>(
                "Paperwork breakdown ID is missing",
                HttpStatus.OK);
        if (Objects.isNull(paperworkBreakdownDate)) return new ResponseEntity<>(
                "Paperwork breakdown date is missing",
                HttpStatus.OK);
        LocalDate localDate = DateUtils.asLocalDateWithFormat(paperworkBreakdownDate);
        PaperworkBreakdown obj = service.findPaperworkBreakdownByDateAndId(paperworksId, localDate);
        if (Objects.nonNull(obj)) {
            return ok(success(PaperworkBreakdownDto.from(obj)).getJson());
        } else {
            return ok(success(PaperworkBreakdownDto.from(localDate)).getJson()); // if Obj is null, then set the paperwork date to the provided date.
        }
    }

    @GetMapping(value = "/find/all/by/paperwork/{paperworksId}")
    public ResponseEntity<Object> findAllPaperworkBreakdownByPaperworkId(@PathVariable Long paperworksId) {
        if (Objects.isNull(paperworksId)) return new ResponseEntity<>(
                "Paperwork breakdown ID is missing",
                HttpStatus.OK);
        List<PaperworkBreakdown> obj = service.findAllPaperworkBreakdownByPaperworkId(paperworksId);
        if (Objects.nonNull(obj)) {
            return ok(success(obj.stream().map(PaperworkBreakdownDto::from).toList()).getJson());
        }
        return null;
    }
}
