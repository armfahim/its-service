package com.its.service.resource.paperworks;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.paperworks.PaperworksDto;
import com.its.service.entity.paperwork.Paperworks;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.response.PaperworksDetailsResponse;
import com.its.service.service.paperworks.PaperworksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/paperwork")
@RequiredArgsConstructor
@Slf4j
public class PaperworkResource {

    private final PaperworksService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody PaperworksDto dto) {
        Paperworks paperworks = new Paperworks();
        dto.to(paperworks);
        try {
            paperworks = service.save(paperworks);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to open new paperwork file", e);
            throw new AlreadyExistsException("A paperwork file for the selected year and month already exists");
        }
        return ok(success(PaperworksDto.from(paperworks), MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String orderColumnName,
                                       @RequestParam(value = "dir", defaultValue = "") String dir,
                                       @RequestParam(value = "year", defaultValue = "") String year,
                                       @RequestParam(value = "month", defaultValue = "") String month,
                                       @RequestParam(value = "paperworkTitle", defaultValue = "") String paperworkTitle) throws Exception {
        try {
            return ok(paginatedSuccess(service.listAndSearch(orderColumnName, dir, page, size, year, month, paperworkTitle)).getJson());
        } catch (Exception e) {
            throw new Exception("Internal Server error");
        }
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Object> find(@PathVariable Long id) {
        return ok(success((PaperworksDetailsResponse.from(service.findById(id)))).getJson());
    }

    @GetMapping(value = "/find/details/{id}")
    public ResponseEntity<Object> findPaperworkAndBreakdownDatesById(@PathVariable Long id) {
        return ok(success(service.findPaperworkAndBreakdownDatesById(id)).getJson());
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        service.delete(id);
        return ok(success(MessageConstant.DATA_DELETE_SUCCESS).getJson());
    }

}
