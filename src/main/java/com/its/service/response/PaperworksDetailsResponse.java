package com.its.service.response;

import com.its.service.entity.paperwork.Paperworks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaperworksDetailsResponse {
    private Long id;
    private String month;
    private String year;
    private String paperworkTitle;
    private Boolean isCompleted;
    private List<LocalDate> existsPaperworkBreakDownDates;
    private LocalDate newInputDate;

    public static PaperworksDetailsResponse from(Paperworks paperworks) {
        PaperworksDetailsResponse response = new PaperworksDetailsResponse();
        BeanUtils.copyProperties(paperworks, response);
        return response;
    }
}
