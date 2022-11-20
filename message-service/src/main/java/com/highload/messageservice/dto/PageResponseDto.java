package com.highload.messageservice.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public PageResponseDto(List<T> data, Pageable request, long totalEntries) {
        this(data,
                totalEntries > (long) (request.getPageNumber() + 1) * request.getPageSize(),
                request.getPageNumber(),
                request.getPageSize(),
                (int) (totalEntries / request.getPageSize() + (totalEntries % request.getPageSize() > 0 ? 1 : 0))
        );
    }
}
