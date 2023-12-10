package com.its.service.utils;

import com.its.service.constant.DefaultConstant;

import java.util.Objects;

/**
 * This class has been created due to convert different kind of values from different criteria
 */
public class ValueConverter {

    /**
     * Check the pass value is null or not, if null then return zero then return the pass value as it is
     */
    public static Integer getNonNullInteger(Integer value) {
        return Objects.isNull(value) ? DefaultConstant.DEFAULT_VALUE_ZERO : value;
    }

    /**
     * Check the pass value is null or not, if null then return zero then return the pass value as it is
     */
    public static Integer getNonNullIntegerWithSum(Integer value1, Integer value2) {
        return getNonNullInteger(value1) + getNonNullInteger(value2);
    }
}
