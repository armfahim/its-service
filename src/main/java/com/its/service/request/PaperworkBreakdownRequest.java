package com.its.service.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PaperworkBreakdownRequest {
    private Long paperworksId;
    private LocalDate paperworkBreakdownDate;
}
