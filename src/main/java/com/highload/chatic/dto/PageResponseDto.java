package com.highload.chatic.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PageResponseDto<T>(
        List<T> page,
        boolean hasNext,
        int currentPage,
        int pageSize
) {
    public PageResponseDto(Page<T> page) {
        this(
                page.toList(),
                page.hasNext(),
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize()
        );
    }

    public PageResponseDto(List<T> page, boolean hasNext, Pageable pageable) {
        this(
                page,
                hasNext,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }
}
