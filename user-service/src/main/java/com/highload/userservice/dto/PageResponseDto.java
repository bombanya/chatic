package com.highload.userservice.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponseDto<T>(
        List<T> page,
        boolean hasNext,
        int currentPage,
        int pageSize,
        int totalPages
) {
    public PageResponseDto(Page<T> page) {
        this(
                page.getContent(),
                page.hasNext(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages()
        );
    }

    public PageResponseDto(List<T> data, Page<?> page) {
        this(data, page.hasNext(), page.getNumber(), page.getNumberOfElements(), page.getTotalPages());
    }
}
