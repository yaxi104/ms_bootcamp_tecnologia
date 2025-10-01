package com.reactive.ms_technology.domain.model;

public class PageInfo {
    private final Integer page;
    private final Integer size;

    public PageInfo(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public static PageInfo of(Integer page, Integer size) {
        int safePage = (page == null || page < 0) ? 0 : page;
        int safeSize = (size == null || size <= 0) ? 10 : size;
        return new PageInfo(safePage, safeSize);
    }
}