package com.its.service.resource;

import com.its.service.constant.MessageConstant;
import com.its.service.dto.InvoiceDetailsDto;
import com.its.service.entity.InvoiceDetails;
import com.its.service.exception.ResourceNotFoundException;
import com.its.service.repository.SupplierDetailsRepository;
import com.its.service.service.InvoiceDetailsService;
import com.its.service.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.its.service.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
public class DashboardResource {

    private final InvoiceDetailsService service;
    private final SupplierDetailsRepository supplierDetailsRepository;

    @GetMapping(value = "/highlights")
    public ResponseEntity<Object> getAll() {
        List<InvoiceDetailsDto> dtos = new ArrayList<>();
        try {
            List<InvoiceDetails> invoiceDetails = service.findAll();
            int d = DateUtils.dateDiffAsPeriod(LocalDate.now(), invoiceDetails.get(0).getPaymentDueDate()).getDays();
            dtos = invoiceDetails.stream().filter(invoice -> DateUtils.dateDiffAsPeriod(LocalDate.now(), invoice.getPaymentDueDate()).getDays() > 3)
                    .collect(Collectors.toList())
                    .stream()
                    .map(InvoiceDetailsDto::from)
                    .collect(Collectors.toList());
//            dtos = invoiceDetails.stream().map(InvoiceDetailsDto::from).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageConstant.NOT_FOUND);
        }
        return ok(success(dtos, MessageConstant.SUCCESS).getJson());
    }
}
