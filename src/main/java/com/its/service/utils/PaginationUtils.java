package com.its.service.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationUtils {

    /**
     * Sort with attribute
     *
     * @param sortBy sort by which attribute of entity
     * @param page   traverse in which page
     * @param size   number of items per request
     * @return corresponding pageable, or null
     */
    public static Pageable getPageable(String sortBy, String dir, Integer page, Integer size) {
        Pageable pageable;
        if (sortBy.isEmpty()) pageable = PageRequest.of(page - 1, size);
        else {
            pageable = PageRequest.of(page - 1, size, Sort.by(dir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
//            String[] parts = sortBy.split(",");
//            pageable = PageRequest.of(page - 1, size, Sort.by(parts[0].trim()));
//            if (parts[1].trim().equals("desc"))
//                pageable = PageRequest.of(page - 1, size, Sort.by(parts[0].trim()).descending());
        }
        return pageable;
    }

    public static PaginatedResponse getPaginatedResponse(Page<?> pageData, List<?> data) {

        PaginatedResponse response = new PaginatedResponse();
        Pageable pageable = pageData.getPageable();
        int currentPage = pageable.getPageNumber() + 1;
        Map<String, Object> meta = new HashMap<>();
        meta.put("currentPage", currentPage);
        meta.put("nextPage", currentPage + 1);
        meta.put("previousPage", currentPage - 1);
        meta.put("size", pageable.getPageSize());
        meta.put("total", pageData.getTotalElements());

        response.setList(data);
        response.setMeta(meta);

        return response;
    }

}
