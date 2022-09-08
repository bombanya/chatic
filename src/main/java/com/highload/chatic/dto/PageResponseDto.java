package com.highload.chatic.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseDto<T> {
    private final List<T> page;
    private final boolean hasNext;

    public PageResponseDto(List<T> page, boolean hasNext) {
        this.page = page;
        this.hasNext = hasNext;
    }

    public List<T> getPage() {
        return page;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
