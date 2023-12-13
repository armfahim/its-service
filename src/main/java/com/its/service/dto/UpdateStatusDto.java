package com.its.service.dto;

import com.its.service.enums.RecordStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateStatusDto {

    @NotNull
    private Long id;
    @NotNull
    private RecordStatus status;

}
