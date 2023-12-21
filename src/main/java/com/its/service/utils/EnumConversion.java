package com.its.service.utils;

import com.its.service.dto.EnumDTO;

import java.util.ArrayList;
import java.util.List;

public final class EnumConversion {
	
	/**
	 * 
	 * @param <E>
	 * @param enumClass
	 * @return
	 */
	public static <E extends Enum<E>> List<EnumDTO> enumToKeyValue(final Class<E> enumClass){
		List<EnumDTO> enumList = new ArrayList<EnumDTO>();
		for (Enum<?> item : enumClass.getEnumConstants()) {
			EnumDTO eDto = new EnumDTO();
			eDto.setKey(item.name());
			eDto.setValue(item.toString());
			enumList.add(eDto);
		}
		return enumList;
	}

}
