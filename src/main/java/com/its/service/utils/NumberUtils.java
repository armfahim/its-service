package com.its.service.utils;

import java.util.Objects;

public class NumberUtils {

    public static Double getZeroIfNull(Double value){
        return Objects.isNull(value) ? 0 : value;
    }
}
