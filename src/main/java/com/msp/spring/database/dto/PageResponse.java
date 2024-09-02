package com.msp.spring.database.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
       return new PageResponse<T>(page.getContent(),
                new Metadata(page.getSize(), page.getNumber(), page.getTotalElements()));
    }

    @Value
    public static class Metadata {
        int size;
        int page;
        long totalElements;
    }
}
