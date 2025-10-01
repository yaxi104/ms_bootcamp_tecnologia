package com.reactive.ms_technology.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageResultTest {

    @Test
    void constructorShouldCalculateTotalPagesCorrectly() {
        List<String> content = List.of("Java", "Python", "Go");
        PageResult<String> result = new PageResult<>(content, 1, 3, 10);

        assertEquals(content, result.getContent());
        assertEquals(1, result.getPage());
        assertEquals(3, result.getSize());
        assertEquals(10, result.getTotalElements());
        assertEquals(4, result.getTotalPages());
    }

    @Test
    void calculateTotalPagesShouldReturnZeroIfSizeIsZero() {
        PageResult<String> result = new PageResult<>(List.of(), 0, 0, 10);
        assertEquals(0, result.getTotalPages());
    }

    @Test
    void setSizeShouldRecalculateTotalPages() {
        PageResult<String> result = new PageResult<>(List.of(), 0, 2, 10);
        result.setSize(5);
        assertEquals(2, result.getTotalPages());
    }

    @Test
    void setTotalElementsShouldRecalculateTotalPages() {
        PageResult<String> result = new PageResult<>(List.of(), 0, 4, 8);
        result.setTotalElements(9);
        assertEquals(3, result.getTotalPages());
    }

    @Test
    void setContentShouldUpdateContent() {
        PageResult<String> result = new PageResult<>();
        List<String> content = List.of("Java", "Python");
        result.setContent(content);
        assertEquals(content, result.getContent());
    }

    @Test
    void setPageShouldUpdatePage() {
        PageResult<String> result = new PageResult<>();
        result.setPage(2);
        assertEquals(2, result.getPage());
    }

    @Test
    void setSizeShouldUpdateSizeAndRecalculatePages() {
        PageResult<String> result = new PageResult<>();
        result.setTotalElements(12);
        result.setSize(5);
        assertEquals(3, result.getTotalPages());
    }

    @Test
    void setTotalPagesShouldOverrideCalculatedValue() {
        PageResult<String> result = new PageResult<>(List.of(), 0, 3, 10);
        result.setTotalPages(99);
        assertEquals(99, result.getTotalPages());
    }
}