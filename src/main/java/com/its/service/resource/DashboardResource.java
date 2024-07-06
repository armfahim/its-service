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

    private final DashboardService dashboardService;

    @GetMapping(value = "/highlights")
    public ResponseEntity<Object> getHighlights() {
        return ok(success(dashboardService.getHighlights(), MessageConstant.SUCCESS).getJson());
    }

    @GetMapping(value = "/pending-invoices")
    public ResponseEntity<Object> pendingInvoices(@RequestParam int dayToSelectPendingInvoice) {
        return ok(success(dashboardService.getPendingInvoices(dayToSelectPendingInvoice), MessageConstant.SUCCESS).getJson());
    }
}
