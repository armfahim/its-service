package com.its.service.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginatedResponse {

    public Object list;

    public Object meta;

}
