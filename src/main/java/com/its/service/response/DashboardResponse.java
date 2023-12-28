package com.its.service.response;

import com.its.service.dto.InvoiceDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {

    private int totalSuppliers;
    private int totalInvoices;
    private List<InvoiceDetailsResponse> invoiceDetails;
}
