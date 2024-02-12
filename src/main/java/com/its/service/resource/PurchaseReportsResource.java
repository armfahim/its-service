package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.service.PurchaseReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/purchase")
@RequiredArgsConstructor
public class PurchaseReportsResource {

    private final PurchaseReportsService purchaseReportsService;

    @GetMapping(value = "/month-wise")
    public ResponseEntity<Object> getTotalPurchaseMonthWise(@RequestParam(required = false) String year,
                                                            @RequestParam(required = false) String month) {
        return ok(success(purchaseReportsService.getTotalPurchaseMonthWise(year, month), MessageConstant.SUCCESS).getJson());
    }

    @GetMapping(value = "/amount/by/supplier")
    public ResponseEntity<Object> getPurchaseAmountBySupplier(@RequestParam(required = false) Long supplierId) {
        return ok(success(purchaseReportsService.getPurchaseAmountBySupplier(supplierId), MessageConstant.SUCCESS).getJson());
    }

    @GetMapping(value = "/amount/by/supplier/year-month")
    public ResponseEntity<Object> getPurchaseAmountBySupplierAndYearInMonth(@RequestParam(required = false) String year,
                                                                             @RequestParam(required = false) Long supplierId) {
        return ok(success(purchaseReportsService.getPurchaseAmountBySupplierAndYearInMonth(year, supplierId), MessageConstant.SUCCESS).getJson());
    }
}
