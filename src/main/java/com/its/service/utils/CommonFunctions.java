package com.its.service.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CommonFunctions {

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> collection) {
        List<T> newList = collection.stream()
                .map(clazz::cast)
                .collect(Collectors.toList());
        return newList;
    }

    public <T> T deserializeObject(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(content,
                    valueType);

        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
