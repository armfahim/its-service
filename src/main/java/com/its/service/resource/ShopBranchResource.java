package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.entity.ShopBranch;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.helper.BasicAudit;
import com.its.service.service.ShopBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.its.service.utils.ResponseBuilder.paginatedSuccess;
import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/shop/branch")
@RequiredArgsConstructor
@Slf4j
public class ShopBranchResource {

    private final ShopBranchService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody ShopBranch ShopBranch) {

        try {
            BasicAudit.setAttributeForCreateUpdate(ShopBranch, false, RecordStatus.ACTIVE);
            ShopBranch = service.save(ShopBranch);
        } catch (DataIntegrityViolationException e) {
            log.error(MessageConstant.INTERNAL_SERVER_ERROR, e);
            throw new AlreadyExistsException(MessageConstant.ALREADY_EXIST);
        }
        return ok(success(ShopBranch, MessageConstant.DATA_SAVE_SUCCESS).getJson());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody ShopBranch dto) {
        ShopBranch shopBranch = service.findById(dto.getId());
        shopBranch.setBranchName(dto.getBranchName());
        shopBranch.setCity(dto.getCity());
        shopBranch.setAddress(dto.getAddress());
        shopBranch = service.update(shopBranch);
        return ok(success(shopBranch, MessageConstant.DATA_UPDATE_SUCCESS).getJson());
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        ShopBranch shopBranch = service.findById(id);
        service.delete(shopBranch);
        return ok(success(MessageConstant.DATA_DELETE_SUCCESS).getJson());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String orderColumnName,
                                       @RequestParam(value = "dir", defaultValue = "") String dir,
                                       @RequestParam(value = "branchName", defaultValue = "") String branchName) {
        return ok(paginatedSuccess(service.listAndSearch(orderColumnName, dir, page, size, branchName)).getJson());
    }

}
