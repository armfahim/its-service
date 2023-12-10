package com.its.service.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class CommonConversion {

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> collection) {   
	    List<T> newList = collection.stream()
	    		.map(clazz::cast)
	    		.collect(Collectors.toList());
	    return newList;
	}
}
