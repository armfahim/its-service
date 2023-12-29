package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.DashboardService;
import com.its.service.service.InvoiceDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
public class DashboardResource {

    private final InvoiceDetailsService service;
    private final SupplierDetailsRepository supplierDetailsRepository;
    private final DashboardService dashboardService;

    @GetMapping(value = "/highlights")
    public ResponseEntity<Object> getHighlights(@RequestParam int dayToSelectDueInvoice) {
        return ok(success(dashboardService.getHighlights(dayToSelectDueInvoice), MessageConstant.SUCCESS).getJson());
    }
}
