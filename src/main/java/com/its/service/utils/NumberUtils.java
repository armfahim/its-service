package com.its.service.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberUtils {

    public static Double getZeroIfNull(Double value){
        return Objects.isNull(value) ? 0 : value;
    }

    public static String getRoundOffValue(BigDecimal number) {
         return String.format("%,.2f",number);
    }
}
