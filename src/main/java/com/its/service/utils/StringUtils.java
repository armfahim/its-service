package com.its.service.utils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return Objects.nonNull(str) && str.trim().length() > 0;
    }

    public static boolean nonNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public static boolean isNull(Long l) {
        return Objects.nonNull(l) && l > 0;
    }

    public static boolean isNotEmpty(Long l) {
        return Objects.nonNull(l) && l > 0;
    }

    public static boolean isEmpty(Long l) {
        return !isNotEmpty(l);
    }

    public static boolean isNotEmpty(Integer integer) {
        return Objects.nonNull(integer) && integer > 0;
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    public static boolean isEmpty(Integer integer) {
        return !isNotEmpty(integer);
    }

    public static boolean isEmptyArr(Set<?> strArr) {
        return strArr.size() == 0;
    }

    public static boolean isNumericString(String code) {
        return code.matches("[0-9]+");
    }

    public static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings).anyMatch(StringUtils::isEmpty);
    }

    public static boolean isAllNotEmpty(String... strings) {
        return Arrays.stream(strings).noneMatch(StringUtils::isEmpty);
    }

    public static boolean isNotEmpty(Object obj) {
        return Objects.nonNull(obj);
    }

    public static String trim(String str) {
        return str.trim();
    }

    public static String objectToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static String randomString() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(100000000000L, 899999999999L));
    }

    public static Long getRecordId(Long previousId) {
        Long i = previousId == null ? 0 : previousId;
        i = i + 1;
        return i;
    }

    /**
     * A common method for all enums since they can't have another base class
     *
     * @param <T>    Enum type
     * @param c      enum type. All enums must be all caps.
     * @param string case insensitive
     * @return corresponding enum, or null
     */
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }

    /**
     * convert english digit to bangla
     * @param string
     * @return
     */
    public static String getStringInBangla(String string) {
        Character[] bangla_number = {'০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'};
        Character[] eng_number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder values = new StringBuilder();
        char[] character = string.toCharArray();
        for (char value : character) {
            char c = ' ';
            for (int j = 0; j < eng_number.length; j++) {
                if (value == eng_number[j]) {
                    c = bangla_number[j];
                    break;
                } else {
                    c = value;
                }
            }
            values.append(c);
        }
        return values.toString();
    }
}
