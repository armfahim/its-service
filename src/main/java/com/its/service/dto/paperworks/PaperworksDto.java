package com.its.service.dto.paperworks;

import com.its.service.dto.SupplierDetailsDto;
import com.its.service.entity.SupplierDetails;
import com.its.service.entity.paperwork.Paperworks;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaperworksDto {

    private Long id;

    @NotNull
    private String month;

    @NotNull
    private String year;

    private String paperworkTitle;
    private Boolean isCompleted;

    public Paperworks to(Paperworks paperworks) {
        paperworks.setMonth(month);
        paperworks.setYear(year);
        return paperworks;
    }

    public static PaperworksDto from(Paperworks paperworks) {
        PaperworksDto dto = new PaperworksDto();
        BeanUtils.copyProperties(paperworks, dto);
        return dto;
    }

}
